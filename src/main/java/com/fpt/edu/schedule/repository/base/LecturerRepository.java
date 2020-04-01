package com.fpt.edu.schedule.repository.base;

import com.fpt.edu.schedule.model.Lecturer;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.Repository;

public interface LecturerRepository extends Repository<Lecturer,String>,JpaSpecificationExecutor<Lecturer> {

    Lecturer save(Lecturer lecturer);

    Lecturer findByGoogleId(String id);

    Lecturer findByShortName(String shortName);

    Lecturer findByEmail(String email);

    void removeByGoogleId(String id);

    Lecturer findById(int id);

}
