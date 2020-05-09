package com.fpt.edu.schedule.service.base;

import com.fpt.edu.schedule.common.enums.StatusLecturer;
import com.fpt.edu.schedule.model.Lecturer;
import com.fpt.edu.schedule.model.Semester;
import com.fpt.edu.schedule.model.Slot;
import com.fpt.edu.schedule.model.Subject;
import com.fpt.edu.schedule.repository.base.QueryParam;

import java.util.List;
import java.util.Set;

public interface LecturerService {

    Lecturer addLecture(Lecturer lecture, String hodGoogleId);

    void remove(int id);

    QueryParam.PagedResultSet<Lecturer> findByCriteria(QueryParam queryParam, int semesterId);

    Lecturer findByGoogleId(String id);

    Lecturer updateLecturerName(Lecturer lecturer);

    Lecturer transferRole(String hodGoogleId, String lecturerGoogleId);

    Lecturer findByShortName(String shortName);

    Lecturer findById(int id);

    Lecturer changeStatus(StatusLecturer status, String googleId);

    List<Lecturer> findForUpdate(int timetableDetailId, QueryParam queryParam);

    Set<Lecturer> getLecturersCanTeachSubject(Subject subject, Semester semester);

    Set<Lecturer> getLecturersCanTeachSlot(Slot slot, Semester semester);

}
