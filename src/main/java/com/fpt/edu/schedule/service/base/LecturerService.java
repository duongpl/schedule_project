package com.fpt.edu.schedule.service.base;

import com.fpt.edu.schedule.common.enums.Status;
import com.fpt.edu.schedule.model.Lecturer;
import com.fpt.edu.schedule.repository.base.QueryParam;

import java.util.List;

public interface LecturerService {

    Lecturer addUser(Lecturer user);

    List<Lecturer> findByCriteria(QueryParam queryParam);

    Lecturer getLecturerNameById(String id);

    Lecturer updateLecturerName(Lecturer lecturer);

    Lecturer updateStatus(Status status, String userId);

}
