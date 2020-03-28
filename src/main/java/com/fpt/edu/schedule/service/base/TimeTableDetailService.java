package com.fpt.edu.schedule.service.base;

import com.fpt.edu.schedule.model.TimetableDetail;
import com.fpt.edu.schedule.repository.base.QueryParam;

import java.util.List;

public interface TimeTableDetailService {
    void addTimeTableDetail(TimetableDetail timetableDetail);

    List<TimetableDetail> findByCriteria(QueryParam queryParam);
}
