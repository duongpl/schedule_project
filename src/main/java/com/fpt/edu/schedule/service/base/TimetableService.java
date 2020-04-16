package com.fpt.edu.schedule.service.base;

import com.fpt.edu.schedule.model.Semester;
import com.fpt.edu.schedule.model.Timetable;

public interface TimetableService {
    Timetable save(Timetable timeTable);

    Timetable findBySemester(Semester semester);
    
    String autoArrange(int semesterId,String hodGoogleId);

    void stop(String googleId);
}
