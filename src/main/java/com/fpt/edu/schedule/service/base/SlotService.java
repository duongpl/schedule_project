package com.fpt.edu.schedule.service.base;

import com.fpt.edu.schedule.model.Slot;
import com.fpt.edu.schedule.repository.base.QueryParam;

import java.util.List;

public interface SlotService {
    void addSlot(Slot slot);

    Slot getSlotByName(String name);
    List<Slot> findByCriteria(QueryParam queryParam);
}
