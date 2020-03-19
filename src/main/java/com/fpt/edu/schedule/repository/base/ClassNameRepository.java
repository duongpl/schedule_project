package com.fpt.edu.schedule.repository.base;

import com.fpt.edu.schedule.model.ClassName;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
public interface ClassNameRepository extends JpaRepository<ClassName, Long>, JpaSpecificationExecutor<ClassName> {
    ClassName save(ClassName className);

    ClassName findByName(String name);
}
