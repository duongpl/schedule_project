package com.fpt.edu.schedule.service.impl;


import com.fpt.edu.schedule.ai.lib.Class;
import com.fpt.edu.schedule.ai.lib.ExpectedSlot;
import com.fpt.edu.schedule.ai.lib.ExpectedSubject;
import com.fpt.edu.schedule.ai.lib.Room;
import com.fpt.edu.schedule.ai.lib.Subject;
import com.fpt.edu.schedule.ai.lib.*;
import com.fpt.edu.schedule.ai.model.*;
import com.fpt.edu.schedule.common.enums.StatusLecturer;
import com.fpt.edu.schedule.common.exception.InvalidRequestException;
import com.fpt.edu.schedule.dto.Runs;
import com.fpt.edu.schedule.dto.TimetableDetailDTO;
import com.fpt.edu.schedule.dto.TimetableProcess;
import com.fpt.edu.schedule.model.*;
import com.fpt.edu.schedule.repository.base.*;
import com.fpt.edu.schedule.service.base.SemesterService;
import com.fpt.edu.schedule.service.base.SubjectService;
import com.fpt.edu.schedule.service.base.TimetableService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TimetableServiceImpl implements TimetableService {
    public static final int POPULATION_SIZE = 150;

    @Autowired
    TimetableProcess timetableProcess;

    SemesterService semesterService;
    TimetableRepository timetableRepo;
    ExpectedRepository expectedRepo;
    LecturerRepository lecturerRepo;
    SubjectService subjectService;
    SubjectRepository subjectRepo;
    SlotRepository slotRepository;
    SemesterRepository semesterRepo;
    RoomRepository roomRepo;
    TimetableDetailRepository timetableDetailRepo;
    ConfirmationRepository confirmationRepo;
    @Autowired
    private ApplicationContext applicationContext;


    @Override
    public Timetable findBySemesterTempFalse(Semester semester) {
        Timetable timetable = timetableRepo.findBySemesterAndTempFalse(semester);
        if (timetable == null) {
            throw new InvalidRequestException("Don't have data for this timetable");
        }
        return timetable;
    }

    @Async
    @Override
    public void autoArrange(int semesterId, String lecturerId, GaParameter gaParameter) {
        Vector<Teacher> teacherModels = new Vector<>();
        Vector<Subject> subjectModels = new Vector<>();
        Vector<com.fpt.edu.schedule.ai.lib.Class> classModels = new Vector<>();
        Vector<ExpectedSlot> expectedSlotModels = new Vector<>();
        Vector<ExpectedSubject> expectedSubjectModel = new Vector<>();
        Vector<SlotGroup> slotGroups = new Vector<>();
        Lecturer lecturer = lecturerRepo.findByGoogleId(lecturerId);
        Semester semester = semesterService.findById(semesterId);
        Timetable timetable = timetableRepo.findBySemesterAndTempTrue(semester);
        checkGaParameter(gaParameter);
        convertData(teacherModels, subjectModels, classModels, expectedSlotModels, expectedSubjectModel, semesterId, lecturerId, slotGroups, lecturer, semester, timetable);
        //To do: lay parameter info tu fe truyen vao bien gaParameter
        GeneticAlgorithm ga = applicationContext.getBean(GeneticAlgorithm.class);
        Model model = new Model(teacherModels, slotGroups, subjectModels, classModels, expectedSlotModels, expectedSubjectModel, gaParameter);
        Population population = new Population(POPULATION_SIZE, model);
        setAttributeForGa(ga, population, model, lecturerId, gaParameter.getStepGeneration());
        checkExistedGa(lecturerId);

        timetableProcess.getMap().put(lecturerId, ga);
        ga.start();
        System.out.println("-------------------------Start-----LecturerId :" + lecturerId);
    }

    @Override
    public void stop(String lecturerId) {
        timetableProcess.getMap().get(lecturerId).stop();
        System.out.println("-------------------------Stop-----LecturerId :" + lecturerId);
    }

    @Override
    public QueryParam.PagedResultSet<Runs> getGenerationInfo(String lecturerId, int page, int limit) {
        QueryParam.PagedResultSet<Runs> pagedResultSet = new QueryParam.PagedResultSet<>();
        GeneticAlgorithm ge = timetableProcess.getMap().get(lecturerId);

        pagedResultSet.setRunning(ge == null ? false : ge.isRunning());
        if (ge == null) {
            pagedResultSet.setResults(new ArrayList<>());
            pagedResultSet.setPage(page);
            return pagedResultSet;
        }
        Queue runsList = ge.getGenInfos();
        pagedResultSet.setTotalCount(runsList.size());
        List<Runs> runsListComplete = (List<Runs>) runsList.stream()
                .sorted(Comparator.comparingInt(Runs::getId))
                .skip((page - 1) * limit)
                .limit(limit)
                .collect(Collectors.toList());
        pagedResultSet.setResults(runsListComplete);
        pagedResultSet.setSize(runsListComplete.size());
        pagedResultSet.setPage(page);
        System.out.println("size-map-------------" + timetableProcess.getMap().size());
        return pagedResultSet;
    }

    @Override
    public void setDefaultTimetable(int runId, String lecturerId, int semesterId) {
        Queue<Runs> q = timetableProcess.getMap().get(lecturerId).getGenInfos();
        Runs runs = q.stream()
                .filter(i -> i.getId() == runId)
                .findAny()
                .orElse(null);
        List<TimetableDetailDTO> timetableDetails = runs.getTimetableEdit()
                .stream()
                .map(i -> i.getTimetable())
                .flatMap(List::stream)
                .collect(Collectors.toList());
        Timetable timetable = timetableRepo.findBySemesterAndTempFalse(semesterRepo.findById(semesterId));
        Map<Integer, TimetableDetail> timetableMap = timetable.getTimetableDetails().stream().collect(
                Collectors.toMap(x -> x.getLineId(), x -> x));
        timetableDetails.stream().forEach(i -> {
            timetableMap.get(i.getLineId()).setLecturer(lecturerRepo.findByShortName(i.getLecturerShortName()));
            timetableMap.get(i.getLineId()).setRoom(roomRepo.findByName(i.getRoom()));

        });
        timetable.setTimetableDetails(new ArrayList(timetableMap.values()));
        timetableRepo.save(timetable);
    }

    private void checkExistedGa(String lecturerId) {
        if (timetableProcess.getMap().get(lecturerId) != null && !timetableProcess.getMap().get(lecturerId).isRunning()) {
            timetableProcess.getMap().remove(lecturerId);
        }
        if (timetableProcess.getMap().get(lecturerId) != null && timetableProcess.getMap().get(lecturerId).isRunning()) {
            throw new InvalidRequestException("Running arrange !");
        }
    }

    private void setAttributeForGa(GeneticAlgorithm ga, Population population, Model model, String lecturerId, int step) {
        ga.setPopulation(population);
        ga.setGeneration(0);
        ga.setModel(model);
        ga.setStepGen(step);
        ga.setRunning(true);
        ga.setLecturerId(lecturerId);
    }

    private void checkGaParameter(GaParameter gaParam) {
        Cofficient cofficient = gaParam.getCofficient();
        if (cofficient.getSlotCoff() + cofficient.getSubjectCoff() + cofficient.getNumberOfClassCoff() + cofficient.getDistanceCoff() != 1) {
            throw new InvalidRequestException("Sum of slotCoff,subjectCoff,numberOfClassCoff,distanceCoff must be equal 1!");
        }
        if (cofficient.getStdCoff() + cofficient.getSatisfactionSumCoff() != 1) {
            throw new InvalidRequestException("Sum of stdCoff,satisfactionSum must be equal 1!");
        }
        if (cofficient.getFulltimeCoff() + cofficient.getParttimeCoff() != 1) {
            throw new InvalidRequestException("Sum of fullTimeCoff,PartimeCoff must be equal 1!");
        }
        if (cofficient.getHardConstraintCoff() + cofficient.getSoftConstraintCoff() != 1) {
            throw new InvalidRequestException("Sum of hardCoff,SoftCoff must be equal 1!");
        }

    }

    private boolean isDraft(Lecturer lecturer, Semester semester) {
        Confirmation confirmation = confirmationRepo.findBySemesterAndLecturer(semester, lecturer);
        if (confirmation != null) {
            return false;
        }
        return true;
    }
    private boolean isOnlineSubject(com.fpt.edu.schedule.model.Subject subject){
        return Character.isAlphabetic(subject.getCode().charAt(subject.getCode().length() - 1));
    }

    private void convertData(Vector<Teacher> teacherModels, Vector<Subject> subjectModels,
                             Vector<com.fpt.edu.schedule.ai.lib.Class> classModels, Vector<ExpectedSlot> expectedSlotModels,
                             Vector<ExpectedSubject> expectedSubjectModel,
                             int semesterId, String lecturerId, Vector<SlotGroup> slots, Lecturer lecturer, Semester semester, Timetable timetable) {

        List<Lecturer> lecturersPublic = confirmationRepo.findAllBySemester(semester).stream().map(Confirmation::getLecturer).collect(Collectors.toList());
        List<Integer> lineIdPublic = timetableRepo.findBySemesterAndTempFalse(semester)
                .getTimetableDetails()
                .stream()
                .filter(i->lecturersPublic.contains(i.getLecturer()))
                .map(TimetableDetail::getLineId)
                .collect(Collectors.toList());
     ;
        List<TimetableDetail> timetableDetails = timetable.getTimetableDetails()
                .stream()
                .filter(i -> i.getSubject().getDepartment().equals(lecturer.getDepartment()) && !isOnlineSubject(i.getSubject()) && !lineIdPublic.contains(i.getLineId()))
                .collect(Collectors.toList());
        List<Expected> expected = expectedRepo.findAllBySemester(semester);
        List<com.fpt.edu.schedule.model.Subject> subjects = subjectService.getAllSubjectBySemester(semesterId, lecturerId);
        //teacher model
        List<Lecturer> lecturers = lecturerRepo.findAllByDepartmentAndStatus(lecturer.getDepartment(), StatusLecturer.ACTIVATE).stream()
                .filter(i -> expectedRepo.findBySemesterAndLecturer(semester, i) != null && isDraft(i, semester))
                .collect(Collectors.toList());
        lecturers.forEach(i -> {
            Expected expectedEach = expectedRepo.findBySemesterAndLecturer(semester, i);
            teacherModels.add(new Teacher(i.getEmail(), i.getFullName(), i.getId(), i.isFullTime() ? 1 : 0, expectedEach.getExpectedNote().getExpectedNumOfClass(), expectedEach.getExpectedNote().getMaxConsecutiveSlot(), i.getQuotaClass()));
        });
        //expected model
        expected.stream()
                .filter(i -> lecturers.contains(i.getLecturer()))
                .forEach(i -> {
                    i.getExpectedSlots().forEach(s -> {
                        expectedSlotModels.add(new ExpectedSlot(s.getExpected().getLecturer().getId(), slotRepository.findByName(s.getSlotName()).getId(), s.getLevelOfPrefer()));
                    });
                    i.getExpectedSubjects().stream().filter(x->!isOnlineSubject(subjectRepo.findByCode(x.getSubjectCode()))).forEach(s -> {
                        expectedSubjectModel.add(new ExpectedSubject(s.getExpected().getLecturer().getId(), subjectRepo.findByCode(s.getSubjectCode()).getId(), s.getLevelOfPrefer()));
                    });
                });
        //class model
        timetableDetails.stream().forEach(i -> {
            classModels.add(new Class(i.getClassName().getName(), i.getSlot().getId(), i.getSubject().getId(), new Room(i.getRoom().getName()), i.getId(), Class.OK));
        });
        //subject model
        // exclude online subject
        subjects.stream().filter(i->!isOnlineSubject(i)).forEach(i -> {
            subjectModels.add(new Subject(i.getCode(), i.getId()));
        });

        //slot model
        SlotGroup m246 = new SlotGroup(3);
        m246.addSlot(new com.fpt.edu.schedule.ai.lib.Slot("M1", 3));
        m246.addSlot(new com.fpt.edu.schedule.ai.lib.Slot("M2", 2));
        m246.addSlot(new com.fpt.edu.schedule.ai.lib.Slot("M3", 1));
        SlotGroup e246 = new SlotGroup(3);

        e246.addSlot(new com.fpt.edu.schedule.ai.lib.Slot("E1", 10));
        e246.addSlot(new com.fpt.edu.schedule.ai.lib.Slot("E2", 7));
        e246.addSlot(new com.fpt.edu.schedule.ai.lib.Slot("E3", 8));

        SlotGroup m35 = new SlotGroup(1);

        m35.addSlot(new com.fpt.edu.schedule.ai.lib.Slot("M4", 4));
        m35.addSlot(new com.fpt.edu.schedule.ai.lib.Slot("M5", 5));

        SlotGroup e35 = new SlotGroup(1);

        e35.addSlot(new com.fpt.edu.schedule.ai.lib.Slot("E4", 9));
        e35.addSlot(new com.fpt.edu.schedule.ai.lib.Slot("E5", 6));
        slots.add(m246);
        slots.add(e246);
        slots.add(m35);
        slots.add(e35);

    }


    private void importDataFromFile() {
        try {

            File slotFile = ResourceUtils.getFile("classpath:data/teacher_slot_real.xml");
            File subjectFile = ResourceUtils.getFile("classpath:data/teacher_subject_real.xml");
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder buider = factory.newDocumentBuilder();
            Document doc = buider.parse(slotFile);
            List<String> slot = new ArrayList<>();

            Element lst = doc.getDocumentElement();

            NodeList teacherList = lst.getElementsByTagName("Row");

            System.out.println(teacherList);
            System.out.println("----------------------------");
            for (int i = 0; i < teacherList.getLength(); i++) {
                Expected expected = new Expected();
                Node node = teacherList.item(i);
                List<com.fpt.edu.schedule.model.ExpectedSlot> expectedSlots = new ArrayList<>();
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element e = (Element) node;
                    if (i == 0) {
                        for (int j = 1; j <= 10; j++) {
                            slot.add(e.getElementsByTagName("Cell").item(j).getTextContent());
                        }
                        continue;
                    }
                    String teacherMail = e.getElementsByTagName("Cell").item(0).getTextContent();
                    Lecturer lecturer = lecturerRepo.findByEmail(teacherMail);
                    System.out.println(e.getElementsByTagName("Cell").item(0).getTextContent());
                    ExpectedNote expectedNote = new ExpectedNote();
                    expectedNote.setExpected(expected);
                    expectedNote.setExpectedNumOfClass(Integer.parseInt(e.getElementsByTagName("Cell").item(slot.size() + 1).getTextContent()));
                    expectedNote.setMaxConsecutiveSlot(Integer.parseInt(e.getElementsByTagName("Cell").item(slot.size() + 2).getTextContent()));

                    for (int t = 0; t < slot.size(); t++) {
                        com.fpt.edu.schedule.model.ExpectedSlot expectedSlot = new com.fpt.edu.schedule.model.ExpectedSlot();
                        int levelPrefer = Integer.parseInt(e.getElementsByTagName("Cell").item(t + 1).getTextContent());
                        expectedSlot.setSlotName(slot.get(t));
                        System.out.println(slot.get(t) + " :" + levelPrefer);
                        expectedSlot.setExpected(expected);
                        expectedSlot.setLevelOfPrefer(Integer.parseInt(e.getElementsByTagName("Cell").item(t + 1).getTextContent()));
                        System.out.println();
                        expectedSlots.add(expectedSlot);
                    }
                    expected.setSemester(semesterRepo.getAllByNowIsTrue());
                    expected.setLecturer(lecturer);
                    expected.setExpectedSlots(expectedSlots);
                    expected.setExpectedNote(expectedNote);
                    expectedRepo.save(expected);

                }
            }
            factory = DocumentBuilderFactory.newInstance();
            buider = factory.newDocumentBuilder();
            doc = buider.parse(subjectFile);
            List<String> subject = new ArrayList<>();

            lst = doc.getDocumentElement();

            NodeList subjectList = lst.getElementsByTagName("Row");

            System.out.println(subjectList);
            System.out.println("----------------------------");
            for (int i = 0; i < subjectList.getLength(); i++) {

                Node node = subjectList.item(i);
                List<com.fpt.edu.schedule.model.ExpectedSubject> expectedSubjects = new ArrayList<>();
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element e = (Element) node;
                    if (i == 0) {
                        for (int j = 1; j <= 17; j++) {
                            String subjectCode = e.getElementsByTagName("Cell").item(j).getTextContent();

                            subject.add(subjectCode);
                        }
                        System.out.println("All subject " + subject);
                        continue;
                    }
                    String teacherMail = e.getElementsByTagName("Cell").item(0).getTextContent();
                    Lecturer lecturer = lecturerRepo.findByEmail(teacherMail);
                    Expected expected = expectedRepo.findBySemesterAndLecturer(semesterRepo.getAllByNowIsTrue(), lecturer);
                    System.out.println(teacherMail);
                    ;

                    for (int t = 0; t < subject.size(); t++) {
                        String subjectCode = subject.get(t);
                        if (subjectRepo.findByCode(subjectCode) == null) {
                            continue;
                        }
                        com.fpt.edu.schedule.model.ExpectedSubject expectedSubject = new com.fpt.edu.schedule.model.ExpectedSubject();
                        expectedSubject.setSubjectCode(subject.get(t));
                        expectedSubject.setExpected(expected);
                        expectedSubject.setLevelOfPrefer(Integer.parseInt(e.getElementsByTagName("Cell").item(t + 1).getTextContent()));
                        expectedSubjects.add(expectedSubject);


                    }
                    expected.setExpectedSubjects(expectedSubjects);
                    expectedRepo.save(expected);

                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

