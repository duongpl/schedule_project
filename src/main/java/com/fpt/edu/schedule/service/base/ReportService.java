package com.fpt.edu.schedule.service.base;

import com.fpt.edu.schedule.model.Report;
import com.fpt.edu.schedule.repository.base.QueryParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ReportService {
    void generateExcelFile(MultipartFile multipartFile, int semesterId);

    Report addReport(Report report,String lecturerId);

    Report updateReport(Report report);


    void removeReportById(int id);

    List<Report> findByCriteria(QueryParam queryParam);
}
