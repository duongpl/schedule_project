package com.fpt.edu.schedule.repository.base;

import com.fpt.edu.schedule.model.Expected;
import com.fpt.edu.schedule.model.ExpectedSlot;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpectedSlotRepository extends JpaRepository<ExpectedSlot,Integer> {
    void removeAllByExpected(Expected expected);

    ExpectedSlot save (ExpectedSlot expectedSlot);

    ExpectedSlot findById(int id);
}
