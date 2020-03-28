package com.fpt.edu.schedule.service.base;

import com.fpt.edu.schedule.dto.TimetableEdit;
import com.fpt.edu.schedule.model.Timetable;

import java.util.List;

public interface TimetableService {
    Timetable save(Timetable timeTable);

    List<TimetableEdit> getTimetableBySemester(int semesterId);
}
