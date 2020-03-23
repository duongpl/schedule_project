package com.fpt.edu.schedule.service.base;

import com.fpt.edu.schedule.model.Schedule;
import com.fpt.edu.schedule.repository.base.QueryParam;

import java.util.List;

public interface ScheduleService  {
    void addSchedule(Schedule schedule);

    List<Schedule> getAllSchedule();

    List<Schedule> findByCriteria(QueryParam queryParam);

    Schedule getScheduleByUserId(String userId);
}
