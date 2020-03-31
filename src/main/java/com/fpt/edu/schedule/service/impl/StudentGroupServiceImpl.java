package com.fpt.edu.schedule.service.impl;

import com.fpt.edu.schedule.model.StudentGroup;
import com.fpt.edu.schedule.repository.base.StudentGroupRepository;
import com.fpt.edu.schedule.service.base.StudentGroupService;
import com.fpt.edu.schedule.service.base.TimeTableDetailService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@AllArgsConstructor
public class StudentGroupServiceImpl implements StudentGroupService {
    StudentGroupRepository studentGroupRepository;
    TimeTableDetailService timeTableDetailService;
    @Override
    public void addStudentGroup(StudentGroup studentGroup) {
        studentGroupRepository.save(studentGroup);

    }




}
