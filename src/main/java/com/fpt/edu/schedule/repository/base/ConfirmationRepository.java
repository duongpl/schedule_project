package com.fpt.edu.schedule.repository.base;

import com.fpt.edu.schedule.model.Confirmation;
import com.fpt.edu.schedule.model.Lecturer;
import com.fpt.edu.schedule.model.Semester;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConfirmationRepository extends JpaRepository<Confirmation, Integer> {
     Confirmation save (Confirmation confirm);

     List<Confirmation> findAllBySemester(Semester semester);

     Confirmation findBySemesterAndLecturer(Semester semester, Lecturer lec);

     Confirmation findById(int id);

     void deleteById(int id);

}
