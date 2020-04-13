package com.fpt.edu.schedule.service.impl;

import com.fpt.edu.schedule.common.exception.InvalidRequestException;
import com.fpt.edu.schedule.model.Room;
import com.fpt.edu.schedule.model.Timetable;
import com.fpt.edu.schedule.model.TimetableDetail;
import com.fpt.edu.schedule.repository.base.BaseSpecifications;
import com.fpt.edu.schedule.repository.base.QueryParam;
import com.fpt.edu.schedule.repository.base.RoomRepository;
import com.fpt.edu.schedule.repository.base.TimetableDetailRepository;
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
    TimetableDetailRepository timetableDetailRepository;

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
        Set<Room> rooms = timetable.getTimetableDetails().stream().filter(i->i.getRoom()!=null).map(TimetableDetail::getRoom).collect(Collectors.toSet());
        return rooms.stream().collect(Collectors.toList());
    }

    @Override
    public List<Room> getRoomForUpdate(int timetableDetailId) {
        TimetableDetail timetableDetail = timetableDetailRepository.findById(timetableDetailId);
        Timetable timetable = timetableDetail.getTimetable();
        Set<Room> rooms1 =timetable.getTimetableDetails().stream().map(TimetableDetail::getRoom).collect(Collectors.toSet());
        Set<Room> rooms2 = timetable.getTimetableDetails().stream().filter(i->i.getSlot().getId() == timetableDetail.getSlot().getId()).map(TimetableDetail::getRoom).collect(Collectors.toSet());
        List<Room> rooms3 = rooms1.stream().filter(i->!rooms2.contains(i) && i!=null).collect(Collectors.toList());
        return rooms3;
    }


}
