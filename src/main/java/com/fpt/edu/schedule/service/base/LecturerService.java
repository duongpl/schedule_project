package com.fpt.edu.schedule.service.base;

import com.fpt.edu.schedule.model.Lecturer;
import com.fpt.edu.schedule.repository.base.QueryParam;

import java.util.List;

public interface LecturerService {

    Lecturer addLecture(Lecturer lecture,String hodGoogleId);

    void remove(int id);

    List<Lecturer> findByCriteria(QueryParam queryParam);

    Lecturer findByGoogleId(String id);

    Lecturer updateLecturerName(Lecturer lecturer);

    Lecturer transferRole(String hodGoogleId,String lecturerGoogleId);

    Lecturer findByShortName(String shortName);

}
