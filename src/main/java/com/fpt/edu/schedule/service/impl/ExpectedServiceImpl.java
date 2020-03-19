package com.fpt.edu.schedule.service.impl;

import com.fpt.edu.schedule.common.exception.InvalidRequestException;
import com.fpt.edu.schedule.dto.ExpectedDTO;
import com.fpt.edu.schedule.model.ExpectedNote;
import com.fpt.edu.schedule.model.Slot;
import com.fpt.edu.schedule.model.Subject;
import com.fpt.edu.schedule.model.UserName;
import com.fpt.edu.schedule.repository.base.ExpectedNoteRepository;
import com.fpt.edu.schedule.service.base.ExpectedService;
import com.fpt.edu.schedule.service.base.SlotService;
import com.fpt.edu.schedule.service.base.SubjectService;
import com.fpt.edu.schedule.service.base.UserService;
import javassist.tools.web.BadHttpRequest;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ExpectedServiceImpl implements ExpectedService {
    UserService userService;
    SubjectService subjectService;
    SlotService slotService;
    ExpectedNoteRepository expectedNoteRepository;

    @Override
    public void addExpected(ExpectedDTO expectedDTO) {
        List<Subject> subjectList = new ArrayList<>();
        expectedDTO.getSubjects().forEach(i -> {
            if (subjectService.getSubjectByCode(i) == null) {
                throw new InvalidRequestException("Not found this subject!");
            }
            subjectList.add(subjectService.getSubjectByCode(i));
        });
        List<Slot> slotList = new ArrayList<>();
        expectedDTO.getSlots().forEach(i -> {
            if (slotService.getSlotByName(i) == null) {
                throw new InvalidRequestException("Not found this slot!");
            }
            slotList.add(slotService.getSlotByName(i));
        });
        UserName userName = userService.getUserNameById(expectedDTO.getUserId());
        ExpectedNote expectedNote = getExpectedNoteByUserName(userName);
        if(expectedNote == null) {
            expectedNote = new ExpectedNote();
        }
        expectedNote.setNote(expectedDTO.getNote());
        expectedNote.setNumberOfClass(expectedDTO.getMaxClass());
        expectedNote.setUserName(userService.getUserNameById(expectedDTO.getUserId()));
        userName.setExpectedNote(expectedNote);
        userName.setExpectedSlot(slotList);
        userName.setExpectedSubject(subjectList);
        userService.addUser(userName);
    }

    @Override
    public ExpectedDTO getExpectedByUserId(String userId) {
        UserName userName = userService.getUserNameById(userId);
        ExpectedDTO expectedDTO = new ExpectedDTO();
        if(userName.getExpectedNote() != null) {
            expectedDTO.setMaxClass(userName.getExpectedNote().getNumberOfClass());
            expectedDTO.setNote(userName.getExpectedNote().getNote());
        }
        List<String> slots = userName.getExpectedSlot().stream().map(i -> i.getName()).collect(Collectors.toList());
        List<String> subjects = userName.getExpectedSubject().stream().map(i -> i.getCode()).collect(Collectors.toList());
        expectedDTO.setSlots(slots);
        expectedDTO.setSubjects(subjects);
        expectedDTO.setUserId(userId);
        return expectedDTO;
    }

    @Override
    public ExpectedNote getExpectedNoteByUserName(UserName userName) {
        return expectedNoteRepository.findByUserName(userName);
    }
}
