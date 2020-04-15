package com.fpt.edu.schedule.repository.base;

import com.fpt.edu.schedule.model.Expected;
import com.fpt.edu.schedule.model.Lecturer;
import com.fpt.edu.schedule.model.Semester;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface ExpectedRepository extends Repository<Expected,Integer>, JpaSpecificationExecutor<Expected> {

    Expected save (Expected expected);

    Expected findById(int id);

    void removeExpectedById(int id);

    Expected findBySemesterAndLecturer(Semester semester, Lecturer lecturer);

    void deleteAllBySemester(Semester semester);

    List<Expected> findAllBySemester(Semester semester);


}
