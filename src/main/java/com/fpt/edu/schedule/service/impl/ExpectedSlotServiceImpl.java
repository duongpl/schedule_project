package com.fpt.edu.schedule.service.impl;

import com.fpt.edu.schedule.common.exception.InvalidRequestException;
import com.fpt.edu.schedule.model.ExpectedSlot;
import com.fpt.edu.schedule.repository.base.ExpectedSlotRepository;
import com.fpt.edu.schedule.service.base.ExpectedSlotService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class ExpectedSlotServiceImpl implements ExpectedSlotService {
        ExpectedSlotRepository expectedSlotRepository;
    @Override
    public ExpectedSlot update(ExpectedSlot expectedSlot) {
        ExpectedSlot existed = expectedSlotRepository.findById(expectedSlot.getId());
        if(existed == null){
            throw new InvalidRequestException("Not found this expected slot !");
        }
        existed.setLevelOfPrefer(expectedSlot.getLevelOfPrefer());
        return expectedSlotRepository.save(existed);
    }
}
