package com.fpt.edu.schedule.service.base;

import com.fpt.edu.schedule.model.ClassName;

import java.util.List;

public interface ClassNameService {
    void addClassName(ClassName className);

    List<ClassName> getAllClass();
}
