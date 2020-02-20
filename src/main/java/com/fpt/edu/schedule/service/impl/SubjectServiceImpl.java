package com.fpt.edu.schedule.service.impl;

import com.fpt.edu.schedule.model.Subject;
import com.fpt.edu.schedule.repository.base.SubjectRepository;
import com.fpt.edu.schedule.service.base.SubjectService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SubjectServiceImpl implements SubjectService {
    SubjectRepository subjectRepository;

    @Override
    public void addSubject(Subject subject) {

        subjectRepository.save(subject);

    }
}
