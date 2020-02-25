package com.fpt.edu.schedule.service.base;

import com.fpt.edu.schedule.model.Schedule;

import java.util.List;

public interface ScheduleService {
    void addSchedule(Schedule schedule);

    List<Schedule> getAllSchedule();
}
