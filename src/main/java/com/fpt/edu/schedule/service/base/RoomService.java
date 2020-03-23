package com.fpt.edu.schedule.service.base;

import com.fpt.edu.schedule.model.Room;
import com.fpt.edu.schedule.repository.impl.QueryParam;

import java.util.List;

public interface RoomService {
    void addRoom(Room room);

    Room getRoombyName(String name);

    List<Room> findByCriteria(QueryParam queryParam);
}
