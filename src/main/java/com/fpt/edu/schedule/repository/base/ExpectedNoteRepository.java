package com.fpt.edu.schedule.repository.base;

import com.fpt.edu.schedule.model.Expected;
import com.fpt.edu.schedule.model.ExpectedNote;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpectedNoteRepository extends JpaRepository<ExpectedNote,Integer> {
    void removeAllByExpected(Expected expected);
}
