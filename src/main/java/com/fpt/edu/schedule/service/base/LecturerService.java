package com.fpt.edu.schedule.service.base;

import com.fpt.edu.schedule.common.enums.Status;
import com.fpt.edu.schedule.model.Lecturer;
import com.fpt.edu.schedule.repository.base.QueryParam;

import java.util.List;

public interface LecturerService {

    Lecturer addLecture(Lecturer lecture);

    void remove(String id);

    List<Lecturer> findByCriteria(QueryParam queryParam);

    Lecturer getLecturerGoogleId(String id);

    Lecturer updateLecturerName(Lecturer lecturer);

    Lecturer updateStatus(Status status, String userId);

    Lecturer findByShortName(String shortName);

}
