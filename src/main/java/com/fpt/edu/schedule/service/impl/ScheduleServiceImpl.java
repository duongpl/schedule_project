package com.fpt.edu.schedule.service.impl;

import com.fpt.edu.schedule.model.Schedule;
import com.fpt.edu.schedule.repository.base.ScheduleRepository;
import com.fpt.edu.schedule.service.base.ScheduleService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {

    ScheduleRepository scheduleRepository;
    @Override
    public void addSchedule(Schedule schedule) {
        scheduleRepository.save(schedule);
    }
}
