package com.fpt.edu.schedule.service.impl;


import com.fpt.edu.schedule.common.exception.InvalidRequestException;
import com.fpt.edu.schedule.model.*;
import com.fpt.edu.schedule.repository.base.BaseSpecifications;
import com.fpt.edu.schedule.repository.base.ExpectedRepository;
import com.fpt.edu.schedule.repository.impl.QueryParam;
import com.fpt.edu.schedule.service.base.ExpectedService;
import com.fpt.edu.schedule.service.base.SlotService;
import com.fpt.edu.schedule.service.base.SubjectService;
import com.fpt.edu.schedule.service.base.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author Duc Anh
 * @since 17/3/2020
 */
@Service
@AllArgsConstructor
public class ExpectedServiceImpl implements ExpectedService {
    UserService userService;
    SubjectService subjectService;
    SlotService slotService;
    ExpectedRepository expectedRepository;

    @Override
    public Expected addExpected(Expected expected) {
        expected.setCreatedDate(new Date());
        expected.setUpdatedDate(new Date());
        UserName userName = userService.getUserNameById(expected.getUserName().getId());
        if(userName == null){
            throw new InvalidRequestException("Don't find user!");
        }
        expected.setUserName(userName);
        expected.getExpectedNote().setExpected(expected);
        expected.getExpectedSlots().stream().forEach( i -> i.setExpected(expected));
        expected.getExpectedSubjects().stream().forEach( i -> i.setExpected(expected));
        return expectedRepository.save(expected);
    }

    @Override
    public Expected updateExpected(Expected expected) {
        Expected existedExpected = expectedRepository.findById(expected.getId());
        if(existedExpected ==null){
            throw new InvalidRequestException("Don't find this expected");
        }
        existedExpected.setExpectedNote(expected.getExpectedNote()!=null ? expected.getExpectedNote() : existedExpected.getExpectedNote());
        existedExpected.setExpectedSlots(expected.getExpectedSlots()!=null ? expected.getExpectedSlots() : existedExpected.getExpectedSlots());
        existedExpected.setExpectedSubjects(expected.getExpectedSubjects()!=null ? expected.getExpectedSubjects() : existedExpected.getExpectedSubjects());
        existedExpected.setUpdatedDate(new Date());
        return expectedRepository.save(existedExpected);
    }

    @Override
    public List<Expected> findByCriteria(QueryParam queryParam) {
        BaseSpecifications cns = new BaseSpecifications(queryParam);

        return expectedRepository.findAll(cns);
    }

    @Override
    public Expected removeExpectedById(int expectedId) {
        Expected existedExpected = expectedRepository.findById(expectedId);
        if(existedExpected ==null){
            throw new InvalidRequestException("Don't find this expected");
        }
        expectedRepository.removeById(expectedId);
        return expectedRepository.removeById(expectedId);
    }


}
