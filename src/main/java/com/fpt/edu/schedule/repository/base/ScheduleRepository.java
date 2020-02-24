package com.fpt.edu.schedule.repository.base;

import com.fpt.edu.schedule.model.Schedule;
import org.springframework.data.repository.Repository;

public interface ScheduleRepository extends Repository<Schedule, Integer> {
    void save(Schedule schedule);
}
