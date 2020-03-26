package com.fpt.edu.schedule.service.base;

import com.fpt.edu.schedule.model.Semester;

public interface SemesterService {
    int countAllSemester();

    Semester save(Semester semester);
}
