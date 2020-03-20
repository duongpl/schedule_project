package com.fpt.edu.schedule.service.impl;


import com.fpt.edu.schedule.model.*;
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


@Service
@AllArgsConstructor
public class ExpectedServiceImpl implements ExpectedService {
    UserService userService;
    SubjectService subjectService;
    SlotService slotService;
    ExpectedRepository expectedRepository;

    @Override
    public void addExpected(Expected expected) {
        expected.setCreatedDate(new Date());
        expectedRepository.save(expected);
    }

    @Override
    public List<Expected> findByCriteria(QueryParam queryParam) {
        return null;
    }


}
