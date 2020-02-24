package com.fpt.edu.schedule.service.impl;

import com.fpt.edu.schedule.model.Slot;
import com.fpt.edu.schedule.repository.base.SlotRepository;
import com.fpt.edu.schedule.service.base.SlotService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

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
}
