package com.fpt.edu.schedule.service.base;

import com.fpt.edu.schedule.model.Room;
import com.fpt.edu.schedule.model.TimetableDetail;
import com.fpt.edu.schedule.repository.base.QueryParam;

import java.util.List;

public interface RoomService {
    void addRoom(Room room);

    Room getRoomByName(String name);

    List<Room> findByCriteria(QueryParam queryParam,String semesterId,String lecturerId);

    List<Room> getRoomForUpdate(TimetableDetail timetableDetail);
}
