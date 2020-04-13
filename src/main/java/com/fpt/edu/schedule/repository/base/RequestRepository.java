package com.fpt.edu.schedule.repository.base;

import com.fpt.edu.schedule.model.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface RequestRepository extends JpaRepository<Request,Integer>, JpaSpecificationExecutor<Request> {
    Request save(Request request);

    void removeById(int id);

    Request findReportById(int id);
}
