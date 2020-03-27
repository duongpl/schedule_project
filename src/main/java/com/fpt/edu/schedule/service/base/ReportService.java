package com.fpt.edu.schedule.service.base;

import com.fpt.edu.schedule.common.enums.Status;
import com.fpt.edu.schedule.model.Report;
import com.fpt.edu.schedule.repository.base.QueryParam;
import java.util.List;

public interface ReportService {
    void generateExcelFile(String fileName,int semesterId);

    Report addReport(Report report);

    Report updateReport(Report report);

    Report updateStatus(Status status, int reportId);

    void removeReportById(int id);

    List<Report> findByCriteria(QueryParam queryParam);
}
