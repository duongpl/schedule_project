package com.fpt.edu.schedule.service.impl;

import com.fpt.edu.schedule.model.Semester;
import com.fpt.edu.schedule.repository.base.SemesterRepository;
import com.fpt.edu.schedule.service.base.SemesterService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@AllArgsConstructor
public class SemesterServiceImpl implements SemesterService {
    SemesterRepository semesterRepository;
    @Override
    public int countAllSemester() {
        return semesterRepository.count();
    }

    @Override
    public Semester save(Semester semester) {
        return semesterRepository.save(semester);
    }
}
