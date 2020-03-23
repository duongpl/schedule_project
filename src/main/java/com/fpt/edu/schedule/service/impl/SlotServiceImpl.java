package com.fpt.edu.schedule.service.impl;

import com.fpt.edu.schedule.model.Slot;
import com.fpt.edu.schedule.repository.base.BaseSpecifications;
import com.fpt.edu.schedule.repository.base.SlotRepository;
import com.fpt.edu.schedule.repository.impl.QueryParam;
import com.fpt.edu.schedule.service.base.SlotService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class SlotServiceImpl implements SlotService {
    SlotRepository slotRepository;
    @Override
    public void addSlot(Slot slot) {
        slotRepository.save(slot);
    }

    @Override
    public Slot getSlotByName(String name) {
        return slotRepository.findByName(name);
    }

    @Override
    public List<Slot> findByCriteria(QueryParam queryParam) {
            BaseSpecifications cns = new BaseSpecifications(queryParam);
            return slotRepository.findAll(cns);
    }
}
