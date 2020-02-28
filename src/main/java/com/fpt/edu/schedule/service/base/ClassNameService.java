package com.fpt.edu.schedule.service.base;

import com.fpt.edu.schedule.model.ClassName;
import com.fpt.edu.schedule.repository.impl.QueryParam;

import java.util.List;

public interface ClassNameService {
    void addClassName(ClassName className);

    ClassName getClassNameByName(String name);

    List<ClassName> findByCriteria(QueryParam queryParam);
}
