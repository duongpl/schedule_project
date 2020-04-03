package com.fpt.edu.schedule.service.base;

import com.fpt.edu.schedule.model.Subject;
import java.util.List;

public interface SubjectService {
    void addSubject(Subject subject);

    Subject getSubjectByCode(String code);

    List<Subject> getAllSubjectBySemester(int semesterId,String hodGoogleId);
}
