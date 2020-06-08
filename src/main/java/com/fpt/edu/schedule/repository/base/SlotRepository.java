package com.fpt.edu.schedule.repository.base;

import com.fpt.edu.schedule.model.Slot;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface SlotRepository extends Repository<Slot,Integer>, JpaSpecificationExecutor<Slot> {

    void save(Slot slot);
    int countAllById(int id);
    Slot findByName(String name);
    List<Slot> findAll();

}
