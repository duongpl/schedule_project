package com.fpt.edu.schedule.repository.base;

import com.fpt.edu.schedule.model.Semester;
import com.fpt.edu.schedule.model.Timetable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface TimetableRepository extends Repository<Timetable, Integer>, JpaSpecificationExecutor<Timetable> {
    Timetable save(Timetable timeTable);

    Timetable findBySemesterAndTempFalse(Semester semester);
    Timetable findBySemesterAndTempTrue(Semester semester);

    List<Timetable> findAllBySemester(Semester semester);
    @Modifying(clearAutomatically = true)
    @Query(value = "DELETE FROM timetable where id=?1  ", nativeQuery = true)
    void deleteById(int id);


}
