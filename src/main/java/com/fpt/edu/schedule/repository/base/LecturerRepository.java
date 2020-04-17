package com.fpt.edu.schedule.repository.base;

import com.fpt.edu.schedule.model.Lecturer;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface LecturerRepository extends Repository<Lecturer,String>,JpaSpecificationExecutor<Lecturer> {

    Lecturer save(Lecturer lecturer);

    Lecturer findByGoogleId(String id);

    Lecturer findByShortName(String shortName);

    Lecturer findByEmail(String email);

    void removeById(int id);

    Lecturer findById(int id);

    List<Lecturer> findAllByDepartment(String department);

}
