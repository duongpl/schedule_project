package com.fpt.edu.schedule.service.base;

import com.fpt.edu.schedule.dto.Runs;
import com.fpt.edu.schedule.model.Semester;
import com.fpt.edu.schedule.model.Timetable;
import com.fpt.edu.schedule.repository.base.QueryParam;

public interface TimetableService {

    Timetable findBySemesterTempFalse(Semester semester);
    
    void autoArrange(int semesterId,String hodGoogleId);

    void stop(String googleId);

    QueryParam.PagedResultSet<Runs> getGenerationInfo(String lecturerId, int page, int limit);

    void setDefaultTimetable(int runId,String lecturerId,int semesterId);


}
