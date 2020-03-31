package com.fpt.edu.schedule.service.impl;

import com.fpt.edu.schedule.model.Timetable;
import com.fpt.edu.schedule.repository.base.SemesterRepository;
import com.fpt.edu.schedule.repository.base.TimetableRepository;
import com.fpt.edu.schedule.service.base.TimetableService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TimetableServiceImpl implements TimetableService {
    SemesterRepository semesterRepository;
    TimetableRepository timetableRepository;


    @Override
    public Timetable save(Timetable timeTable) {
        return null;
    }

}
