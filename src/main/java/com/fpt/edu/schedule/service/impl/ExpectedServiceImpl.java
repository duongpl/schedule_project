package com.fpt.edu.schedule.service.impl;

import com.fpt.edu.schedule.common.exception.InvalidRequestException;
import com.fpt.edu.schedule.dto.ExpectedDTO;
import com.fpt.edu.schedule.model.ExpectedNote;
import com.fpt.edu.schedule.model.Slot;
import com.fpt.edu.schedule.model.Subject;
import com.fpt.edu.schedule.model.UserName;
import com.fpt.edu.schedule.service.base.ExpectedService;
import com.fpt.edu.schedule.service.base.SlotService;
import com.fpt.edu.schedule.service.base.SubjectService;
import com.fpt.edu.schedule.service.base.UserService;
import javassist.tools.web.BadHttpRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ExpectedServiceImpl implements ExpectedService {
    UserService userService;
    SubjectService subjectService;
    SlotService slotService;

    @Override
    public void addExpected(ExpectedDTO expectedDTO) {
        List<Subject> subjectList = new ArrayList<>();
        expectedDTO.getSubjects().forEach(i->{
            if(subjectService.getSubjectByCode(i) == null){
                throw new InvalidRequestException("Not found this subject!");
            }
            subjectList.add(subjectService.getSubjectByCode(i));
        });
        List<Slot> slotList = new ArrayList<>();
        expectedDTO.getSlots().forEach(i->{
            if(slotService.getSlotByName(i) == null){
                throw new InvalidRequestException("Not found this slot!");
            }
            slotList.add(slotService.getSlotByName(i));
        });
        UserName userName =userService.getUserNameById(expectedDTO.getUserId());
        ExpectedNote expectedNote = new ExpectedNote();
        expectedNote.setNote(expectedDTO.getNote());
        expectedNote.setNumberOfClass(expectedDTO.getMaxClass());
        userName.setExpectedNote(expectedNote);
        userName.setExpectedSlot(slotList);
        userName.setExpectedSubject(subjectList);
        userService.addUser(userName);
    }

    @Override
    public ExpectedDTO getExpectedByUserId(String userId) {

        return null;
    }
}
