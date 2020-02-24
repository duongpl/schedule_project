package com.fpt.edu.schedule.service.base;

import com.fpt.edu.schedule.model.Subject;

public interface SubjectService {
    void addSubject(Subject subject);

    Subject getSubjectByCode(String code);
}
