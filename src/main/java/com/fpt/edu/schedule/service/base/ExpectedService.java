package com.fpt.edu.schedule.service.base;


import com.fpt.edu.schedule.model.Expected;
import com.fpt.edu.schedule.repository.impl.QueryParam;

import java.util.List;


public interface ExpectedService {
    void addExpected(Expected expected);

    List<Expected> findByCriteria(QueryParam queryParam);

}
