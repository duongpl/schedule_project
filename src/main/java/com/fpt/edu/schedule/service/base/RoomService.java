package com.fpt.edu.schedule.service.base;

import com.fpt.edu.schedule.model.Room;

public interface RoomService {
    void addRoom(Room room);

    Room getRoombyName(String name);
}
