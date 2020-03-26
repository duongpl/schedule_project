package com.fpt.edu.schedule.repository.base;

import com.fpt.edu.schedule.model.Room;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.Repository;

public interface RoomRepository extends Repository<Room, Integer>, JpaSpecificationExecutor<Room> {
    void save(Room room);

    Room findByName(String name);
}
