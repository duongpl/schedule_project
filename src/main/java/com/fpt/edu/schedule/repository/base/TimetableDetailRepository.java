package com.fpt.edu.schedule.repository.base;

import com.fpt.edu.schedule.model.Timetable;
import com.fpt.edu.schedule.model.TimetableDetail;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface TimetableDetailRepository extends Repository<TimetableDetail, Integer>, JpaSpecificationExecutor<TimetableDetail> {
    TimetableDetail save(TimetableDetail timetableDetail);

    List<TimetableDetail> findAll();

    void deleteAllByTimetable(Timetable timetable);

    TimetableDetail findById(int id);

}
