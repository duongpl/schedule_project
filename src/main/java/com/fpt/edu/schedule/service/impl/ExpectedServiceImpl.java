package com.fpt.edu.schedule.service.impl;


import com.fpt.edu.schedule.common.exception.InvalidRequestException;
import com.fpt.edu.schedule.dto.ExpectedView;
import com.fpt.edu.schedule.dto.TimetableDetailDTO;
import com.fpt.edu.schedule.model.*;
import com.fpt.edu.schedule.repository.base.*;
import com.fpt.edu.schedule.service.base.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
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
    ExpectedRepository expectedRepo;
    ExpectedSubjectRepository expectedSubjectRepository;
    ExpectedSlotService expectedSlotService;
    ExpectedSubjectService expectedSubjectService;
    ExpectedNoteRepository expectedNoteRepository;
    SemesterRepository semesterRepo;
    TimetableService timetableService;
    private static final List<String> SLOT_LIST = Arrays.asList("M1", "M2", "M3", "M4", "M5", "E1", "E2", "E3", "E4", "E5");

    @Override
    public Expected addExpected(Expected expected) {
        Expected existedExpected = expectedRepo.findBySemesterAndLecturer(semesterRepo.findById(expected.getSemester().getId()),
                lecturerService.findByGoogleId(expected.getLecturer().getGoogleId()));
        if (existedExpected != null) {
            throw new InvalidRequestException("Already have expected for this semester !");
        }
        expected.setCreatedDate(new Date());
        expected.setUpdatedDate(new Date());
        Lecturer lecturer = lecturerService.findByGoogleId(expected.getLecturer().getGoogleId());

        //get all subject and slot request
        List<String> slotRequests = expected.getExpectedSlots().stream()
                .map(ExpectedSlot::getSlotName)
                .collect(Collectors.toList());
        List<String> subjectRequests = expected.getExpectedSubjects().stream()
                .map(ExpectedSubject::getSubjectCode)
                .collect(Collectors.toList());
        List<Subject> subjects = subjectService.getAllSubjectBySemester(expected.getSemester().getId(), lecturer.getGoogleId());
        List<Subject> subjectsNotContainInRequest = subjects.stream()
                .filter(i -> !subjectRequests.contains(i.getCode()))
                .collect(Collectors.toList());
        List<String> slotNotContainInRequest = SLOT_LIST.stream()
                .filter(o -> !slotRequests.contains(o))
                .collect(Collectors.toList());
        subjectsNotContainInRequest.stream()
                .forEach(i -> expected.getExpectedSubjects().add(new ExpectedSubject(i.getCode())));
        slotNotContainInRequest.stream()
                .forEach(i -> expected.getExpectedSlots().add(new ExpectedSlot(i)));
        expected.setSemester(semesterRepo.findById(expected.getSemester().getId()));
        expected.setLecturer(lecturer);
        expected.getExpectedNote().setExpected(expected);

        //assign parent of each child
        expected.getExpectedSlots().stream()
                .forEach(i -> i.setExpected(expected));
        expected.getExpectedSubjects().stream()
                .forEach(i -> i.setExpected(expected));
        checkValidExpected(expected);
        return expectedRepo.save(expected);
    }

    @Transactional
    @Override
    public Expected updateExpected(Expected expected) {
        Expected existedExpected = expectedRepo.findById(expected.getId());
        Lecturer lecturer = existedExpected.getLecturer();
        if (existedExpected == null) {
            throw new InvalidRequestException("Don't find this expected");
        }
        if (expected.getExpectedNote() != null) {
            existedExpected.setExpectedNote(expected.getExpectedNote());
            expectedNoteRepository.removeAllByExpected(existedExpected);
            existedExpected.getExpectedNote().setExpected(existedExpected);
        }
        if (expected.getExpectedSlots() != null) {
            expected.getExpectedSlots().stream().forEach(i -> expectedSlotService.update(i));
        }
        if (expected.getExpectedSubjects() != null) {
            expected.getExpectedSubjects().stream().forEach(i -> expectedSubjectService.update(i));
        }
        checkValidExpected(existedExpected);
        existedExpected.setUpdatedDate(new Date());
        return expectedRepo.save(existedExpected);
    }

    @Override
    public List<Expected> findByCriteria(QueryParam queryParam) {
        BaseSpecifications cns = new BaseSpecifications(queryParam);
        return expectedRepo.findAll(cns);
    }

    @Override
    public void removeExpectedById(int expectedId) {
        Expected existedExpected = expectedRepo.findById(expectedId);
        if (existedExpected == null) {
            throw new InvalidRequestException("Don't find this expected");
        }
        expectedRepo.removeExpectedById(existedExpected.getId());
    }

    @Override
    public Expected getExpectedByLecturerAndSemester(String lecturerId, int semesterId) {
        Expected expected = expectedRepo.findBySemesterAndLecturer(semesterRepo.findById(semesterId),
                lecturerService.findByGoogleId(lecturerId));
        if (expected == null) {
            Expected newExpected = new Expected();
            List<Subject> subjects = subjectService.getAllSubjectBySemester(semesterId, lecturerId);
            List<ExpectedSubject> expectedSubjectList = subjects.stream()
                    .map(i -> new ExpectedSubject(i.getCode()))
                    .collect(Collectors.toList());
            List<ExpectedSlot> expectedSlot = SLOT_LIST.stream()
                    .map(i -> new ExpectedSlot(i))
                    .collect(Collectors.toList());
            newExpected.setExpectedSubjects(expectedSubjectList);
            newExpected.setExpectedSlots(expectedSlot);
            newExpected.setLecturer(lecturerService.findByGoogleId(lecturerId));
            newExpected.setSemester(semesterRepo.findById(semesterId));

            return newExpected;
        }
        if (expectedRepo.findBySemesterAndLecturer(semesterRepo.getAllByNowIsTrue(),
                lecturerService.findByGoogleId(lecturerId)) == null) {
            expected.setCanReuse(true);
        }
        return expected;
    }

    @Override
    public Expected saveExistedExpected(String lecturerGoogleId, int semesterId) {
        Expected expected = expectedRepo.findBySemesterAndLecturer(semesterRepo.findById(semesterId),
                lecturerService.findByGoogleId(lecturerGoogleId));
        if (expected == null) {
            throw new InvalidRequestException("This semester don't have your expected !");
        }
        Expected newExpected = new Expected();
        ExpectedNote expectedNote = expected.getExpectedNote();
        newExpected.setLecturer(expected.getLecturer());
        //convert data
        List<ExpectedSubject> expectedSubjects = expected.getExpectedSubjects().stream()
                .map(i -> new ExpectedSubject(i.getSubjectCode(), i.getLevelOfPrefer(), newExpected))
                .collect(Collectors.toList());
        List<ExpectedSlot> expectedSlots = expected.getExpectedSlots().stream()
                .map(i -> new ExpectedSlot(i.getSlotName(), i.getLevelOfPrefer(), newExpected))
                .collect(Collectors.toList());

        newExpected.setExpectedSubjects(expectedSubjects);
        newExpected.setExpectedSlots(expectedSlots);
        newExpected.setExpectedNote(new ExpectedNote(expectedNote.getExpectedNumOfClass(), expectedNote.getMaxConsecutiveSlot(), expectedNote.getNote(), newExpected));
        newExpected.setUpdatedDate(new Date());
        newExpected.setCreatedDate(new Date());
        newExpected.setSemester(semesterRepo.getAllByNowIsTrue());

        return expectedRepo.save(newExpected);
    }

    @Override
    public Expected getExpectedForViewBySemester(String shortName, int semesterId) {
        Expected expected = expectedRepo.findBySemesterAndLecturer(semesterRepo.findById(semesterId),
                lecturerService.findByShortName(shortName));

        return expected;
    }

    @Override
    public List<ExpectedView> getListExpectedForView(int semesterId, String groupBy) {
        Semester se = semesterRepo.findById(semesterId);
        List<ExpectedView> expectedViewList = new ArrayList<>();
        List<TimetableDetailDTO> expectedDTOs = new ArrayList<>();
        List<Expected> expectedList = expectedRepo.findAllBySemester(se);

        if (groupBy.equals("slot")) {
            List<ExpectedSlot> expectedSlots = expectedList.stream()
                    .map(i -> i.getExpectedSlots())
                    .flatMap(List::stream)
                    .collect(Collectors.toList());
            expectedDTOs = expectedSlots.stream()
                    .distinct()
                    .map(i -> new TimetableDetailDTO(i.getExpected().getLecturer().getShortName(), i.getSlotName(), i.getLevelOfPrefer()))
                    .collect(Collectors.toList());


        } else if (groupBy.equals("subject")) {
            List<ExpectedSubject> expectedSubject = expectedList.stream()
                    .map(i -> i.getExpectedSubjects())
                    .flatMap(List::stream)
                    .collect(Collectors.toList());
            expectedDTOs = expectedSubject.stream()
                    .distinct()
                    .map(i -> new TimetableDetailDTO(i.getExpected().getLecturer().getShortName(), i.getSubjectCode(), i.getLevelOfPrefer()))
                    .collect(Collectors.toList());
        }
        Map<String, List<TimetableDetailDTO>> collect = expectedDTOs
                .stream()
                .collect(Collectors.groupingBy(TimetableDetailDTO::getLecturerShortName));
        expectedViewList = collect
                .entrySet().stream()
                .map(i -> new ExpectedView(i.getKey(), i.getValue()))
                .collect(Collectors.toList());
        expectedViewList.stream().sorted(Comparator.comparing(ExpectedView::getLecturerName));

        return expectedViewList;
    }

    private void checkValidExpected(Expected expected) {
        List<String> subjectRegister = expected.getExpectedSubjects()
                .stream()
                .filter(x -> x.getLevelOfPrefer() != 0)
                .map(ExpectedSubject::getSubjectCode).collect(Collectors.toList());
        List<String> slotRegister = expected.getExpectedSlots()
                .stream()
                .filter(x -> x.getLevelOfPrefer() != 0)
                .map(ExpectedSlot::getSlotName).collect(Collectors.toList());
        if (subjectRegister.size() == 0) {
            throw new InvalidRequestException("You must teach at least one subject!");
        }
        if (slotRegister.size() == 0) {
            throw new InvalidRequestException("You must teach at least one slot!");
        }
    }


}
