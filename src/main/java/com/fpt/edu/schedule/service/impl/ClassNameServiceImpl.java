package com.fpt.edu.schedule.service.impl;

import com.fpt.edu.schedule.model.ClassName;
import com.fpt.edu.schedule.repository.base.ClassNameRepository;
import com.fpt.edu.schedule.service.base.ClassNameService;
import com.fpt.edu.schedule.service.base.TimeTableDetailService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@AllArgsConstructor
public class ClassNameServiceImpl  implements ClassNameService {
    ClassNameRepository classNameRepository;
    TimeTableDetailService timeTableDetailService;
    @Override
    public void addClassName(ClassName className) {
        classNameRepository.save(className);

    }




}
