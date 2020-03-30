package com.fpt.edu.schedule.service.base;


import com.fpt.edu.schedule.model.Expected;
import com.fpt.edu.schedule.repository.base.QueryParam;

import java.util.List;


public interface ExpectedService {
    Expected addExpected(Expected expected);

    Expected updateExpected(Expected expected);

    List<Expected> findByCriteria(QueryParam queryParam);

    void removeExpectedById(int expectedId);

    Expected getExpectedByLecturerAndSemester(String lecturerId,int semesterId);

}
