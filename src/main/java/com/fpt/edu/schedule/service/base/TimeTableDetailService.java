package com.fpt.edu.schedule.service.base;

import com.fpt.edu.schedule.dto.TimetableView;
import com.fpt.edu.schedule.dto.TimetableDetailDTO;
import com.fpt.edu.schedule.dto.TimetableEdit;
import com.fpt.edu.schedule.model.TimetableDetail;
import com.fpt.edu.schedule.repository.base.QueryParam;

import java.util.List;

public interface TimeTableDetailService {
    List<TimetableView> getTimetableForView(QueryParam queryParam);

    List<TimetableDetail> findByCriteria(QueryParam queryParam,int semesterId);

    TimetableDetail updateTimetableDetail(TimetableDetailDTO timetableDetail);

    List<TimetableEdit> getTimetableForEdit(QueryParam queryParam,String groupBy,int semesterId);

    void swapTwoTimetableDetail(List<Integer> ids,String type) ;

    TimetableDetail saveTimetableDetail(TimetableDetail timetableDetail);



}
