package com.fpt.edu.schedule.repository.base;

import com.fpt.edu.schedule.model.*;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.Repository;

public interface TimetableDetailRepository extends Repository<TimetableDetail, Integer>, JpaSpecificationExecutor<TimetableDetail> {
    TimetableDetail save(TimetableDetail timetableDetail);

    void deleteAllByTimetable(Timetable timetable);

    TimetableDetail findById(int id);

    TimetableDetail findBySlotAndRoomAndTimetable(Slot slot, Room room, Timetable timetable);

    TimetableDetail findBySlotAndLecturerAndTimetable(Slot slot, Lecturer lecturer, Timetable timetable);

}
