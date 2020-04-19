package com.fpt.edu.schedule.repository.base;

import com.fpt.edu.schedule.model.*;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface TimetableDetailRepository extends Repository<TimetableDetail, Integer>, JpaSpecificationExecutor<TimetableDetail> {
    TimetableDetail save(TimetableDetail timetableDetail);
    @Modifying(clearAutomatically = true)
    @Query(value = "DELETE FROM timetabledetail\n" +
            "    WHERE timetable_id =?1  ", nativeQuery = true)
    void deleteAllByTimetable(int timetableId);

    TimetableDetail findById(int id);

    TimetableDetail findBySlotAndRoomAndTimetable(Slot slot, Room room, Timetable timetable);


    List<TimetableDetail> findAllByLecturerAndTimetable(Lecturer lecturer, Timetable timetable);

}
