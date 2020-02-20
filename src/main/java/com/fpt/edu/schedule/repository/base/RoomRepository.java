package com.fpt.edu.schedule.repository.base;

import com.fpt.edu.schedule.model.Room;
import org.springframework.data.repository.Repository;

public interface RoomRepository extends Repository<Room,Integer> {
    void save(Room room);
}
