package com.fpt.edu.schedule.service.base;

import com.fpt.edu.schedule.dto.Runs;
import com.fpt.edu.schedule.model.Semester;
import com.fpt.edu.schedule.model.Timetable;
import com.fpt.edu.schedule.repository.base.QueryParam;

public interface TimetableService {
    Timetable save(Timetable timeTable);

    Timetable findBySemester(Semester semester);
    
    void autoArrange(int semesterId,String hodGoogleId);

    void stop(String googleId);

    QueryParam.PagedResultSet<Runs> getGenerationInfo(String lecturerId, int page, int limit);

        
}
