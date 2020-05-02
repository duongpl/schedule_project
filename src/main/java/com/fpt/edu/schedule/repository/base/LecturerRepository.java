package com.fpt.edu.schedule.repository.base;

import com.fpt.edu.schedule.common.enums.StatusLecturer;
import com.fpt.edu.schedule.model.Lecturer;
import com.fpt.edu.schedule.model.Role;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface LecturerRepository extends Repository<Lecturer,String>,JpaSpecificationExecutor<Lecturer>
        ,PagingAndSortingRepository<Lecturer, String> {

    Lecturer save(Lecturer lecturer);

    Lecturer findByGoogleId(String id);

    Lecturer findByShortName(String shortName);

    Lecturer findByEmail(String email);

    void removeById(int id);

    Lecturer findById(int id);

    List<Lecturer> findAllByDepartmentAndStatus(String department, StatusLecturer status);

    Lecturer findAllByDepartmentAndRole(String department, Role role);


}
