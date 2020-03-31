package com.fpt.edu.schedule.repository.base;

import com.fpt.edu.schedule.model.StudentGroup;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
public interface StudentGroupRepository extends JpaRepository<StudentGroup, Long>, JpaSpecificationExecutor<StudentGroup> {
    StudentGroup save(StudentGroup studentGroup);

    StudentGroup findByName(String name);
}
