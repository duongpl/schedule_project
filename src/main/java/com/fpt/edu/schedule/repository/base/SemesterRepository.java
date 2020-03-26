package com.fpt.edu.schedule.repository.base;

import com.fpt.edu.schedule.model.Room;
import com.fpt.edu.schedule.model.Semester;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.Repository;

public interface SemesterRepository extends Repository<Semester, Integer>, JpaSpecificationExecutor<Room> {
   int count();

   Semester save(Semester semester);
}
