package com.fpt.edu.schedule.service.impl;


import com.fpt.edu.schedule.common.exception.InvalidRequestException;
import com.fpt.edu.schedule.model.*;
import com.fpt.edu.schedule.repository.base.*;
import com.fpt.edu.schedule.service.base.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Duc Anh
 * @since 17/3/2020
 */
@Service
@Transactional
@AllArgsConstructor
public class ExpectedServiceImpl implements ExpectedService {
    LecturerService lecturerService;
    SubjectService subjectService;
    SlotService slotService;
    ExpectedRepository expectedRepository;
    ExpectedSubjectRepository expectedSubjectRepository;
    ExpectedSlotService expectedSlotService;
    ExpectedSubjectService expectedSubjectService;
    ExpectedNoteRepository expectedNoteRepository;
    SemesterRepository semesterRepository;
    private static final List<String> SLOT_LIST = Arrays.asList("M1", "M2", "M3", "M4", "M5", "E1", "E2", "E3", "E4", "E5");

    @Override
    public Expected addExpected(Expected expected) {
        expected.setCreatedDate(new Date());
        expected.setUpdatedDate(new Date());
        Lecturer lecturer = lecturerService.findByGoogleId(expected.getLecturer().getGoogleId());
        List<String> slotRequests = expected.getExpectedSlots().stream().map(ExpectedSlot::getSlotName).collect(Collectors.toList());
        List<String> subjectRequests = expected.getExpectedSubjects().stream().map(ExpectedSubject::getSubjectCode).collect(Collectors.toList());
        List<Subject> subjects = subjectService.getAllSubjectBySemester(expected.getSemester().getId(), lecturer.getGoogleId());
        List<Subject> subjectsNotContainInRequest = subjects.stream().filter(i -> !subjectRequests.contains(i.getCode())).collect(Collectors.toList());
        List<String> slotNotContainInRequest = SLOT_LIST.stream().filter(o -> !slotRequests.contains(o)).collect(Collectors.toList());
        subjectsNotContainInRequest.forEach(i -> expected.getExpectedSubjects().add(new ExpectedSubject(i.getCode())));
        slotNotContainInRequest.forEach(i -> expected.getExpectedSlots().add(new ExpectedSlot(i)));
        expected.setSemester(semesterRepository.findById(expected.getSemester().getId()));
        expected.setLecturer(lecturer);
        expected.getExpectedNote().setExpected(expected);
        expected.getExpectedSlots().stream().forEach(i -> i.setExpected(expected));

        expected.getExpectedSubjects().stream().forEach(i -> i.setExpected(expected));
        return expectedRepository.save(expected);
    }

    @Override
    public Expected updateExpected(Expected expected) {
        Expected existedExpected = expectedRepository.findById(expected.getId());
        if (existedExpected == null) {
            throw new InvalidRequestException("Don't find this expected");
        }
        if (expected.getExpectedNote() != null) {
            existedExpected.setExpectedNote(expected.getExpectedNote());
            expectedNoteRepository.removeAllByExpected(existedExpected);
            existedExpected.getExpectedNote().setExpected(existedExpected);
        }
        if (expected.getExpectedSlots() != null) {
            expected.getExpectedSlots().forEach(i -> expectedSlotService.update(i));
        }
        if (expected.getExpectedSubjects() != null) {
            expected.getExpectedSubjects().forEach(i -> expectedSubjectService.update(i));
        }
        existedExpected.setUpdatedDate(new Date());
        return expectedRepository.save(existedExpected);
    }

    @Override
    public List<Expected> findByCriteria(QueryParam queryParam) {
        BaseSpecifications cns = new BaseSpecifications(queryParam);

        return expectedRepository.findAll(cns);
    }

    @Override
    public void removeExpectedById(int expectedId) {
        Expected existedExpected = expectedRepository.findById(expectedId);
        if (existedExpected == null) {
            throw new InvalidRequestException("Don't find this expected");
        }
        expectedRepository.removeExpectedById(existedExpected.getId());
    }

    @Override
    public Expected getExpectedByLecturerAndSemester(String lecturerId, int semesterId) {
        Lecturer lecturer = lecturerService.findByGoogleId(lecturerId);
        Expected expected = expectedRepository.findBySemesterAndLecturer(semesterRepository.findById(semesterId),
                lecturerService.findByGoogleId(lecturerId));
        if (expected == null) {
            Expected newExpected = new Expected();
            List<Subject> subjects = subjectService.getAllSubjectBySemester(semesterId,lecturerId);
            List<ExpectedSubject> expectedSubjectList = subjects.stream().map(i -> new ExpectedSubject(i.getCode())).collect(Collectors.toList());
            List<ExpectedSlot> expectedSlot = SLOT_LIST.stream().map(i -> new ExpectedSlot(i)).collect(Collectors.toList());
            newExpected.setExpectedSubjects(expectedSubjectList);
            newExpected.setExpectedSlots(expectedSlot);
            newExpected.setLecturer(lecturerService.findByGoogleId(lecturerId));
            newExpected.setSemester(semesterRepository.findById(semesterId));

            return newExpected;
        }
        if (expectedRepository.findBySemesterAndLecturer(semesterRepository.getAllByNowIsTrue(),
                lecturerService.findByGoogleId(lecturerId)) == null) {
            expected.setCanReuse(true);
        }
        return expected;
    }

    @Override
    public Expected saveExistedExpected(String lecturerGoogleId, int semesterId) {
        Expected expected = expectedRepository.findBySemesterAndLecturer(semesterRepository.findById(semesterId),
                lecturerService.findByGoogleId(lecturerGoogleId));
        if (expected == null) {
            throw new InvalidRequestException("This semester don't have your expected !");
        }
        Expected newExpected = new Expected();
        newExpected.setLecturer(expected.getLecturer());
        newExpected.setExpectedSubjects(expected.getExpectedSubjects());
        newExpected.setExpectedSlots(expected.getExpectedSlots());
        newExpected.setUpdatedDate(new Date());
        newExpected.setCreatedDate(new Date());
        newExpected.setExpectedNote(expected.getExpectedNote());
        newExpected.setSemester(semesterRepository.getAllByNowIsTrue());

        return expectedRepository.save(newExpected);
    }


}
