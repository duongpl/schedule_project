package com.fpt.edu.schedule.service.base;

import com.fpt.edu.schedule.model.Semester;
import com.fpt.edu.schedule.repository.base.QueryParam;

import java.util.List;

public interface SemesterService {
    int countAllSemester();

    Semester save(Semester semester);

    List<Semester> findByCriteria(QueryParam queryParam);
}
