package com.fpt.edu.schedule.repository.base;

import com.fpt.edu.schedule.model.ClassName;
import org.springframework.data.repository.Repository;

public interface ClassNameRepository extends Repository<ClassName,Integer> {
    void save(ClassName className);
}
