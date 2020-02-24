package com.fpt.edu.schedule.repository.base;

import com.fpt.edu.schedule.model.Slot;
import org.springframework.data.repository.Repository;

public interface SlotRepository extends Repository<Slot,Integer> {

    void save(Slot slot);

    Slot findByName(String name);

}
