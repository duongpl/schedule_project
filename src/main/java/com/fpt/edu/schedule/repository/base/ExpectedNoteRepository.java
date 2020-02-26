package com.fpt.edu.schedule.repository.base;

import com.fpt.edu.schedule.model.ExpectedNote;
import com.fpt.edu.schedule.model.UserName;
import org.springframework.data.repository.Repository;

public interface ExpectedNoteRepository extends Repository<ExpectedNote,Integer> {
    ExpectedNote findByUserName(UserName userName);
}
