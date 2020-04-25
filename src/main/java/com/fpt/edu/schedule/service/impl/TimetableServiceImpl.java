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
    TimetableRepository timetableRepository;
    ExpectedRepository expectedRepository;
    LecturerRepository lecturerRepository;
    SubjectService subjectService;
    SubjectRepository subjectRepository;
    SlotRepository slotRepository;
    SemesterRepository semesterRepository;
    TimetableDetailRepository timetableDetailRepository;
    @Autowired
    private ApplicationContext applicationContext;



    @Override
    public Timetable findBySemesterTempFalse(Semester semester) {
        Timetable timetable = timetableRepository.findBySemesterAndTempFalse(semester);
        if (timetable == null) {
            throw new InvalidRequestException("Don't have data for this timetable");
        }
        return timetable;
    }

    @Async
    @Override
    public void autoArrange(int semesterId, String lecturerId,GaParameter gaParameter) {
        Vector<Teacher> teacherModels = new Vector<>();
        Vector<Subject> subjectModels = new Vector<>();
        Vector<com.fpt.edu.schedule.ai.lib.Class> classModels = new Vector<>();
        Vector<ExpectedSlot> expectedSlotModels = new Vector<>();
        Vector<ExpectedSubject> expectedSubjectModel = new Vector<>();
        Vector<SlotGroup> slotGroups = new Vector<>();
        Lecturer lecturer = lecturerRepository.findByGoogleId(lecturerId);
        Semester semester = semesterService.findById(semesterId);
        Timetable timetable = timetableRepository.findBySemesterAndTempTrue(semester);
        checkGaParameter(gaParameter);
        convertData(teacherModels, subjectModels, classModels, expectedSlotModels, expectedSubjectModel, semesterId, lecturerId, slotGroups, lecturer, semester, timetable);
        //To do: lay parameter info tu fe truyen vao bien gaParameter
        GeneticAlgorithm ga = applicationContext.getBean(GeneticAlgorithm.class);
        Model model = new Model(teacherModels,slotGroups,subjectModels,classModels,expectedSlotModels,expectedSubjectModel, gaParameter);
        Population population = new Population(POPULATION_SIZE, model);
        setAttributeForGa(ga,population,model,lecturerId);
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

        pagedResultSet.setRunning(ge == null ? false : ge.isRun());
        if (ge == null) {
            pagedResultSet.setResults(new ArrayList<>());
            pagedResultSet.setPage(page);
            return pagedResultSet;
        }
        Queue runsList = ge.getGenInfos();
        pagedResultSet.setTotalCount(runsList.size());
        List<Runs> runsListComplete = (List<Runs>) runsList.stream()
                .skip((page - 1) * limit)
                .limit(limit)
                .sorted(Comparator.comparingInt(Runs::getId))
                .collect(Collectors.toList());
        pagedResultSet.setResults(runsListComplete);
        pagedResultSet.setSize(runsListComplete.size());
        pagedResultSet.setPage(page);
        System.out.println("size-map-------------"  +timetableProcess.getMap().size());
        return pagedResultSet;
    }

    @Override
    public void setDefaultTimetable(int runId, String lecturerId, int semesterId) {
        Queue<Runs> q = timetableProcess.getMap().get(lecturerId).getGenInfos();

        List<Runs> runsQueue = new ArrayList<>(timetableProcess.getMap().get(lecturerId).getGenInfos());
        Runs runs = runsQueue.stream()
                .filter(i->i.getId() == runId)
                .findAny()
                .orElse(null);
        List<TimetableDetail> timetableDetails = runs.getTimetableDetails();
        Timetable timetable = timetableRepository.findBySemesterAndTempFalse(semesterRepository.findById(semesterId));
        Map<Integer, TimetableDetail> timetableMap = timetable.getTimetableDetails().stream().collect(
                Collectors.toMap(x -> x.getLineId(), x -> x));
        timetableDetails.forEach(i -> {
            timetableMap.get(i.getLineId()).setLecturer(i.getLecturer());
            timetableMap.get(i.getLineId()).setRoom(i.getRoom());

        });
        timetable.setTimetableDetails(new ArrayList(timetableMap.values()));
        timetableRepository.save(timetable);
    }
    private void checkExistedGa(String lecturerId){
        if (timetableProcess.getMap().get(lecturerId) != null && !timetableProcess.getMap().get(lecturerId).isRun()) {
            timetableProcess.getMap().remove(lecturerId);
        }
        if (timetableProcess.getMap().get(lecturerId) != null && timetableProcess.getMap().get(lecturerId).isRun()) {
            throw new InvalidRequestException("Running arrange !");
        }
    }
    private void setAttributeForGa(GeneticAlgorithm ga,Population population,Model model,String lecturerId){
        ga.setPopulation(population);
        ga.setGeneration(0);
        ga.setModel(model);
        ga.setRun(true);
        ga.setLecturerId(lecturerId);
    }
    private void checkGaParameter(GaParameter gaParam){
        Cofficient cofficient =gaParam.getCofficient();
        if(cofficient.getSlotCoff()+cofficient.getSubjectCoff()+cofficient.getNumberOfClassCoff()+cofficient.getDistanceCoff()!=1){
            throw new InvalidRequestException("Sum of slotCoff,subjectCoff,numberOfClassCoff,distanceCoff must be equal 1!");
        }
        if(cofficient.getStdCoff()+cofficient.getSatisfactionSumCoff() != 1){
            throw new InvalidRequestException("Sum of stdCoff,satisfactionSum must be equal 1!");
        }
        if(cofficient.getFulltimeCoff()+cofficient.getParttimeCoff() != 1){
            throw new InvalidRequestException("Sum of fullTimeCoff,PartimeCoff must be equal 1!");
        }
        if(cofficient.getHardConstraintCoff()+cofficient.getSoftConstraintCoff() !=1){
            throw new InvalidRequestException("Sum of hardCoff,SoftCoff must be equal 1!");
        }

    }
    private void convertData(Vector<Teacher> teacherModels, Vector<Subject> subjectModels,
                             Vector<com.fpt.edu.schedule.ai.lib.Class> classModels, Vector<ExpectedSlot> expectedSlotModels,
                             Vector<ExpectedSubject> expectedSubjectModel,
                             int semesterId, String lecturerId, Vector<SlotGroup> slots, Lecturer lecturer, Semester semester, Timetable timetable) {

        List<TimetableDetail> timetableDetails = timetable.getTimetableDetails()
                .stream()
                .filter(i -> i.getSubject().getDepartment().equals(lecturer.getDepartment()) && !Character.isAlphabetic(i.getSubject().getCode().charAt(i.getSubject().getCode().length() - 1)))
                .collect(Collectors.toList());
        List<Expected> expected = expectedRepository.findAllBySemester(semester);
        List<com.fpt.edu.schedule.model.Subject> subjects = subjectService.getAllSubjectBySemester(semesterId, lecturerId);
        //teacher model
        List<Lecturer> lecturers = lecturerRepository.findAllByDepartmentAndStatus(lecturer.getDepartment(), StatusLecturer.ACTIVATE).stream()
                .filter(i -> expectedRepository.findBySemesterAndLecturer(semester, i) != null)
                .collect(Collectors.toList());
        lecturers.forEach(i -> {
            Expected expectedEach = expectedRepository.findBySemesterAndLecturer(semester, i);
            teacherModels.add(new Teacher(i.getEmail(), i.getFullName(), i.getId(), i.isFullTime() ? 1 : 0, expectedEach.getExpectedNote().getExpectedNumOfClass(), expectedEach.getExpectedNote().getMaxConsecutiveSlot(), i.getQuotaClass()));
        });
        //expected model
        expected.stream()
                .filter(i -> lecturers.contains(i.getLecturer()))
                .forEach(i -> {
            i.getExpectedSlots().forEach(s -> {
                expectedSlotModels.add(new ExpectedSlot(s.getExpected().getLecturer().getId(), slotRepository.findByName(s.getSlotName()).getId(), s.getLevelOfPrefer()));
            });
            i.getExpectedSubjects().forEach(s -> {
                expectedSubjectModel.add(new ExpectedSubject(s.getExpected().getLecturer().getId(), subjectRepository.findByCode(s.getSubjectCode()).getId(), s.getLevelOfPrefer()));
            });
        });
        //class model
        timetableDetails.forEach(i -> {
            classModels.add(new Class(i.getClassName().getName(), i.getSlot().getId(), i.getSubject().getId(), new Room(i.getRoom().getName()), i.getId()));
        });
        //subject model
        subjects.forEach(i -> {
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
                    Lecturer lecturer = lecturerRepository.findByEmail(teacherMail);
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
                    expected.setSemester(semesterRepository.getAllByNowIsTrue());
                    expected.setLecturer(lecturer);
                    expected.setExpectedSlots(expectedSlots);
                    expected.setExpectedNote(expectedNote);
                    expectedRepository.save(expected);

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
                    Lecturer lecturer = lecturerRepository.findByEmail(teacherMail);
                    Expected expected = expectedRepository.findBySemesterAndLecturer(semesterRepository.getAllByNowIsTrue(), lecturer);
                    System.out.println(teacherMail);
                    ;

                    for (int t = 0; t < subject.size(); t++) {
                        String subjectCode = subject.get(t);
                        if (subjectRepository.findByCode(subjectCode) == null) {
                            continue;
                        }
                        com.fpt.edu.schedule.model.ExpectedSubject expectedSubject = new com.fpt.edu.schedule.model.ExpectedSubject();
                        expectedSubject.setSubjectCode(subject.get(t));
                        expectedSubject.setExpected(expected);
                        expectedSubject.setLevelOfPrefer(Integer.parseInt(e.getElementsByTagName("Cell").item(t + 1).getTextContent()));
                        expectedSubjects.add(expectedSubject);


                    }
                    expected.setExpectedSubjects(expectedSubjects);
                    expectedRepository.save(expected);

                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

