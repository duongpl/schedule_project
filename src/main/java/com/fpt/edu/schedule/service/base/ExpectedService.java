package com.fpt.edu.schedule.service.base;

import com.fpt.edu.schedule.dto.ExpectedDTO;
import com.fpt.edu.schedule.model.ExpectedNote;
import com.fpt.edu.schedule.model.UserName;

public interface ExpectedService {
    void addExpected(ExpectedDTO expectedDTO);

    ExpectedDTO getExpectedByUserId(String userId);

    ExpectedNote getExpectedNoteByUserName(UserName userName);
}
