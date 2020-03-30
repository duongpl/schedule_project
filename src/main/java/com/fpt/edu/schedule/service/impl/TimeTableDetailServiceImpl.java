package com.fpt.edu.schedule.service.impl;

import com.fpt.edu.schedule.common.exception.InvalidRequestException;
import com.fpt.edu.schedule.dto.TimetableDetailDTO;
import com.fpt.edu.schedule.model.Lecturer;
import com.fpt.edu.schedule.model.Room;
import com.fpt.edu.schedule.model.TimetableDetail;
import com.fpt.edu.schedule.repository.base.*;
import com.fpt.edu.schedule.service.base.LecturerService;
import com.fpt.edu.schedule.service.base.RoomService;
import com.fpt.edu.schedule.service.base.TimeTableDetailService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
@Transactional
public class TimeTableDetailServiceImpl implements TimeTableDetailService {
    LecturerService lecturerService;
    TimetableDetailRepository timetableDetailRepository;
    RoomService roomService;

    @Override
    public void addTimeTableDetail(TimetableDetail timetableDetail) {

    }


    @Override
    public List<TimetableDetail> findByCriteria(QueryParam queryParam) {
        BaseSpecifications cns = new BaseSpecifications(queryParam);
        return timetableDetailRepository.findAll(cns);
    }

    @Override
    public TimetableDetail updateTimetableDetail(TimetableDetailDTO timetableDetail) {
       TimetableDetail timetableDetailExisted = timetableDetailRepository.findById(timetableDetail.getId());
        if (timetableDetailExisted == null) {
            throw new InvalidRequestException("Not found this timetable !");
        }
        if (timetableDetail.getLecturerShortName() != null) {
            Lecturer lecturer = timetableDetail.getLecturerShortName().equals("NOT_ASSIGN") ? null
                    : lecturerService.findByShortName(timetableDetail.getLecturerShortName());
            timetableDetailExisted.setLecturer(lecturer);
        }
        if (timetableDetail.getRoom() != null) {
            Room room = timetableDetail.getRoom().equals("NOT_ASSIGN") ? null
                    : roomService.getRoomByName(getValidRoom(timetableDetail,timetableDetailExisted));
            timetableDetailExisted.setRoom(room);
        }
        return timetableDetailRepository.save(timetableDetailExisted);
    }

    private String getValidRoom(TimetableDetailDTO timetableDetail,TimetableDetail timetableDetailExisted) {
        QueryParam queryParam = new QueryParam();
        Map<String,Object> criteria = new HashMap<>();
        Map<String,Object> roomMap = new HashMap<>();
        roomMap.put("name",timetableDetail.getRoom());
        Map<String,Object> slotMap = new HashMap<>();
        slotMap.put("name",timetableDetailExisted.getSlot().getName());
        criteria.put("room",roomMap );
        criteria.put("slot",slotMap);
        queryParam.setCriteria(criteria);
        if(findByCriteria(queryParam).size() >0){
            throw new InvalidRequestException ("Already have class in room !");
        }
       return timetableDetail.getRoom();
    }
}
