package com.fpt.edu.schedule.service.impl;

import com.fpt.edu.schedule.ai.lib.Class;
import com.fpt.edu.schedule.ai.lib.ExpectedSlot;
import com.fpt.edu.schedule.ai.lib.ExpectedSubject;
import com.fpt.edu.schedule.ai.lib.Room;
import com.fpt.edu.schedule.ai.lib.Subject;
import com.fpt.edu.schedule.ai.lib.*;
import com.fpt.edu.schedule.ai.model.GeneticAlgorithm;
import com.fpt.edu.schedule.ai.model.Model;
import com.fpt.edu.schedule.ai.model.Train;
import com.fpt.edu.schedule.common.exception.InvalidRequestException;
import com.fpt.edu.schedule.model.*;
import com.fpt.edu.schedule.repository.base.*;
import com.fpt.edu.schedule.service.base.SemesterService;
import com.fpt.edu.schedule.service.base.SubjectService;
import com.fpt.edu.schedule.service.base.TimetableService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.Vector;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TimetableServiceImpl implements TimetableService {
    SemesterService semesterService;
    TimetableRepository timetableRepository;
    ExpectedRepository expectedRepository;
    LecturerRepository lecturerRepository;
    SubjectService subjectService;
    SubjectRepository subjectRepository;
    SlotRepository slotRepository;
    @Autowired
    ApplicationEventPublisher applicationEventPublisher;

    @Override
    public Timetable save(Timetable timeTable) {
        return null;
    }

    @Override
    public Timetable findBySemester(Semester semester) {
        Timetable timetable =timetableRepository.findBySemester(semester);
        if(timetable == null){
            throw new InvalidRequestException("Don't have data for this timetable");
        }
        return timetable;
    }


    @Async
    @Override
    public void autoArrange(int semesterId, String lecturerId) {

//        try {
//            for (int i = 0; i < 10000000; i++) {
//                Thread.sleep(1000);
//                Thread.currentThread().setName(googleId);
//                if (i % 10 == 0) {
//                    applicationEventPublisher.publishEvent(new DataListener(this, i));
//                }
//                System.out.println("Thread of user " + Thread.currentThread().getName() + " value i :" + i);
//            }
//        } catch (InterruptedException e) {
//            Thread.currentThread().interrupt();
//
//        }
        Vector<Teacher> teacherModels = new Vector<>();
        Vector<Subject> subjectModels = new Vector<>();
        Vector<com.fpt.edu.schedule.ai.lib.Class> classModels = new Vector<>();
        Vector<ExpectedSlot> expectedSlotModels = new Vector<>();
        Vector<ExpectedSubject> expectedSubjectModel = new Vector<>();
        Vector<SlotGroup> slotGroups = new Vector<>();

        convertData(teacherModels, subjectModels, classModels, expectedSlotModels, expectedSubjectModel, semesterId, lecturerId, slotGroups);
        int generation = 0;
        Model model = new Model(teacherModels, slotGroups, subjectModels, classModels, expectedSlotModels, expectedSubjectModel);
        Train train = new Train();
        GeneticAlgorithm ge= new GeneticAlgorithm(model,train);
        ge.start();
    }

    @Override
    public void stop(String lecturerId) {
        Set<Thread> setOfThread = Thread.getAllStackTraces().keySet();
        for (Thread thread : setOfThread) {
            if (thread.getName().equals(lecturerId)) {
                thread.interrupt();
                System.out.println("Thead of user " + thread.getName() + " stop");
            }
        }
    }
    private void convertData(Vector<Teacher> teacherModels, Vector<Subject> subjectModels,
                             Vector<com.fpt.edu.schedule.ai.lib.Class> classModels, Vector<ExpectedSlot> expectedSlotModels, Vector<ExpectedSubject> expectedSubjectModel,
                             int semesterId, String lecturerId, Vector<SlotGroup> slots) {
        Lecturer lecturer = lecturerRepository.findByGoogleId(lecturerId);
        Semester semester = semesterService.findById(semesterId);
        Timetable timetable = findBySemester(semester);
        List<TimetableDetail> timetableDetails = timetable.getTimetableDetails().stream().filter(i -> i.getSubject().getDepartment().equals(lecturer.getDepartment())).collect(Collectors.toList());
        List<Expected> expected = expectedRepository.findAllBySemester(semester);
        List<com.fpt.edu.schedule.model.Subject> subjects = subjectService.getAllSubjectBySemester(semesterId, lecturerId);
        //teacher model
        List<Lecturer> lecturers = lecturerRepository.findAllByDepartment(lecturer.getDepartment());
        lecturers.forEach(i -> {
            Expected expectedEach = expectedRepository.findBySemesterAndLecturer(semester, i);
            teacherModels.add(new Teacher(i.getEmail(), i.getFullName(), i.getId(), i.isFullTime() ? 1 : 0, expectedEach.getExpectedNote().getExpectedNumOfClass(), expectedEach.getExpectedNote().getMaxConsecutiveSlot(), i.getQuotaClass()));
        });
        //expected model
        expected.forEach(i -> {
            i.getExpectedSlots().forEach(s -> {
                expectedSlotModels.add(new ExpectedSlot(s.getExpected().getLecturer().getId(), slotRepository.findByName(s.getSlotName()).getId(), s.getLevelOfPrefer()));
            });
            i.getExpectedSubjects().forEach(s -> {
                expectedSubjectModel.add(new ExpectedSubject(s.getExpected().getLecturer().getId(), subjectRepository.findByCode(s.getSubjectCode()).getId(), s.getLevelOfPrefer()));
            });
        });
        timetableDetails.forEach(i -> {
            classModels.add(new Class(i.getClassName().getName(), i.getSlot().getId(), i.getSubject().getId(), new Room(i.getRoom().getName()), i.getId()));
        });
        subjects.forEach(i -> {
            subjectModels.add(new Subject(i.getCode(), i.getId()));
        });


        SlotGroup m246 = new SlotGroup(3);
        m246.addSlot(new com.fpt.edu.schedule.ai.lib.Slot("M1", 1));
        m246.addSlot(new com.fpt.edu.schedule.ai.lib.Slot("M2", 2));
        m246.addSlot(new com.fpt.edu.schedule.ai.lib.Slot("M3", 3));
        SlotGroup e246 = new SlotGroup(3);

        e246.addSlot(new com.fpt.edu.schedule.ai.lib.Slot("E1", 4));
        e246.addSlot(new com.fpt.edu.schedule.ai.lib.Slot("E2", 5));
        e246.addSlot(new com.fpt.edu.schedule.ai.lib.Slot("E3", 6));

        SlotGroup m35 = new SlotGroup(1);

        m35.addSlot(new com.fpt.edu.schedule.ai.lib.Slot("M4", 7));
        m35.addSlot(new com.fpt.edu.schedule.ai.lib.Slot("M5", 8));

        SlotGroup e35 = new SlotGroup(1);

        e35.addSlot(new com.fpt.edu.schedule.ai.lib.Slot("E4", 9));
        e35.addSlot(new com.fpt.edu.schedule.ai.lib.Slot("E5", 10));
        slots.add(m246);
        slots.add(e246);
        slots.add(m35);
        slots.add(e35);


    }



}
