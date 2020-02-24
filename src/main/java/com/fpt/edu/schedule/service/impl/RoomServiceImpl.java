package com.fpt.edu.schedule.service.impl;

import com.fpt.edu.schedule.model.Room;
import com.fpt.edu.schedule.repository.base.RoomRepository;
import com.fpt.edu.schedule.service.base.RoomService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RoomServiceImpl implements RoomService {
    RoomRepository roomRepository;

    @Override
    public void addRoom(Room room) {
        roomRepository.save(room);
    }

    @Override
    public Room getRoombyName(String name) {
       return roomRepository.findByName(name);
    }
}
