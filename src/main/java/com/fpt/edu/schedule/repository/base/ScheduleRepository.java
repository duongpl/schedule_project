package com.fpt.edu.schedule.repository.base;

import com.fpt.edu.schedule.model.Schedule;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface ScheduleRepository extends Repository<Schedule, Integer> {
    void save(Schedule schedule);

    List<Schedule> findAll();
}
