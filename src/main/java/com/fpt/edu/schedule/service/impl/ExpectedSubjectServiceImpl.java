package com.fpt.edu.schedule.service.impl;

import com.fpt.edu.schedule.common.exception.InvalidRequestException;
import com.fpt.edu.schedule.model.ExpectedSubject;
import com.fpt.edu.schedule.repository.base.ExpectedSubjectRepository;
import com.fpt.edu.schedule.service.base.ExpectedSubjectService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class ExpectedSubjectServiceImpl implements ExpectedSubjectService {
    ExpectedSubjectRepository expectedSubjectRepository;
    @Override
    public ExpectedSubject update(ExpectedSubject expectedSlot) {
        ExpectedSubject existed = expectedSubjectRepository.findById(expectedSlot.getId());
        if(existed == null){
            throw new InvalidRequestException("Not found this expected subject !");
        }
        existed.setLevelOfPrefer(expectedSlot.getLevelOfPrefer());
        return expectedSubjectRepository.save(existed);
    }
}
