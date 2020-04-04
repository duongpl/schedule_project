package com.fpt.edu.schedule.service.impl;

import com.fpt.edu.schedule.common.exception.InvalidRequestException;
import com.fpt.edu.schedule.model.Lecturer;
import com.fpt.edu.schedule.model.Room;
import com.fpt.edu.schedule.model.Timetable;
import com.fpt.edu.schedule.model.TimetableDetail;
import com.fpt.edu.schedule.repository.base.BaseSpecifications;
import com.fpt.edu.schedule.repository.base.QueryParam;
import com.fpt.edu.schedule.repository.base.RoomRepository;
import com.fpt.edu.schedule.service.base.LecturerService;
import com.fpt.edu.schedule.service.base.RoomService;
import com.fpt.edu.schedule.service.base.SemesterService;
import com.fpt.edu.schedule.service.base.TimetableService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RoomServiceImpl implements RoomService {
    RoomRepository roomRepository;
    TimetableService timetableService;
    SemesterService semesterService;
    LecturerService lecturerService;

    @Override
    public void addRoom(Room room) {
        roomRepository.save(room);
    }

    @Override
    public Room getRoomByName(String name) {
        Room room = roomRepository.findByName(name);
        if (room == null) {
            throw new InvalidRequestException("Don't find this room");
        }
        return room;
    }

    @Override
    public List<Room> findByCriteria(QueryParam queryParam, String semesterId, String lecturerId) {
        if (semesterId.length() == 0) {
            BaseSpecifications cns = new BaseSpecifications(queryParam);
            return roomRepository.findAll(cns);
        }
        int semester = Integer.parseInt(semesterId);
        Timetable timetable = timetableService.findBySemester(semesterService.findById(semester));
        Lecturer lecturer = lecturerService.findByGoogleId(lecturerId);
        Set<Room> rooms = timetable.getTimetableDetails().stream().filter(i -> i.getSubject().getDepartment().equals(lecturer.getDepartment()))
                .map(TimetableDetail::getRoom).collect(Collectors.toSet());
        return rooms.stream().collect(Collectors.toList());

    }


}
