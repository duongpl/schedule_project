package com.fpt.edu.schedule.service.impl;

import com.fpt.edu.schedule.common.exception.InvalidRequestException;
import com.fpt.edu.schedule.model.Semester;
import com.fpt.edu.schedule.repository.base.BaseSpecifications;
import com.fpt.edu.schedule.repository.base.QueryParam;
import com.fpt.edu.schedule.repository.base.SemesterRepository;
import com.fpt.edu.schedule.service.base.SemesterService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class SemesterServiceImpl implements SemesterService {
    SemesterRepository semesterRepo;

    @Override
    public int countAllSemester() {
        return semesterRepo.count();
    }

    @Override
    public Semester save(Semester semester) {
        return semesterRepo.save(semester);
    }

    @Override
    public List<Semester> findByCriteria(QueryParam queryParam) {

        BaseSpecifications cns = new BaseSpecifications(queryParam);

        return semesterRepo.findAll(cns);

    }

    @Override
    public Semester findById(int id) {
        Semester semester = semesterRepo.findById(id);
        if(semester == null){
            throw new InvalidRequestException("Don't find this semester");
        }
        return semester;
    }
}
