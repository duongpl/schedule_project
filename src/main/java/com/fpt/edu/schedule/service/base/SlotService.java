package com.fpt.edu.schedule.service.base;

import com.fpt.edu.schedule.model.Slot;

public interface SlotService {
    void addSlot(Slot slot);

    Slot getSlotByName(String name);
}
