package com.fpt.edu.schedule.repository.base;

import com.fpt.edu.schedule.model.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ReportRepository extends JpaRepository<Report,Integer>, JpaSpecificationExecutor<Report> {
    Report save(Report report);

    void removeById(int id);

    Report findReportById(int id);
}
