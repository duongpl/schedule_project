package com.fpt.edu.schedule.service.base;

import com.fpt.edu.schedule.dto.ExpectedDTO;

public interface ExpectedService {
    void addExpected(ExpectedDTO expectedDTO);

    ExpectedDTO getExpectedByUserId(String userId);
}
