package com.fpt.edu.schedule.service.impl;

import com.fpt.edu.schedule.common.exception.InvalidRequestException;
import com.fpt.edu.schedule.model.Schedule;
import com.fpt.edu.schedule.model.UserName;
import com.fpt.edu.schedule.repository.base.BaseSpecifications;
import com.fpt.edu.schedule.repository.base.ScheduleRepository;
import com.fpt.edu.schedule.repository.base.UserRepository;
import com.fpt.edu.schedule.repository.base.QueryParam;
import com.fpt.edu.schedule.service.base.ScheduleService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class ScheduleServiceImpl implements ScheduleService {
    UserRepository userRepository;
    ScheduleRepository scheduleRepository;
    @Override
    public void addSchedule(Schedule schedule) {
        scheduleRepository.save(schedule);
    }

    @Override
    public List<Schedule> getAllSchedule() {
        return scheduleRepository.findAll();
    }

    @Override
    public List<Schedule> findByCriteria(QueryParam queryParam) {
        BaseSpecifications cns = new BaseSpecifications(queryParam);

        return scheduleRepository.findAll(cns);
    }

    @Override
    public Schedule getScheduleByUserId(String userId) {
        UserName userName = userRepository.findById(userId);
        if(userName == null){
            throw new InvalidRequestException("Don't find this user!");
        }
        return scheduleRepository.findByUserName(userName);
    }


}
