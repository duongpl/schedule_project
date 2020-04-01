package com.fpt.edu.schedule.repository.base;

import com.fpt.edu.schedule.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface DepartmentRepository extends JpaRepository<Department, Long>, JpaSpecificationExecutor<Department> {
    Department save(Department department);

    Department findByName(String name);
}
