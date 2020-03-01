package com.fpt.edu.schedule.repository.base;

import com.fpt.edu.schedule.model.Schedule;
import com.fpt.edu.schedule.model.UserName;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface ScheduleRepository extends Repository<Schedule, Integer>, JpaSpecificationExecutor<Schedule> {
    void save(Schedule schedule);

    List<Schedule> findAll();

    Schedule findByUserName(UserName userName);
}
