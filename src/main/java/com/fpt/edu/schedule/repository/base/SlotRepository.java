package com.fpt.edu.schedule.repository.base;

import com.fpt.edu.schedule.model.Slot;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.Repository;

public interface SlotRepository extends Repository<Slot,Integer>, JpaSpecificationExecutor<Slot> {

    void save(Slot slot);

    Slot findByName(String name);

}
