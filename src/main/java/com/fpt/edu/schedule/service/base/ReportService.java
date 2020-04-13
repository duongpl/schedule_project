package com.fpt.edu.schedule.service.base;

import com.fpt.edu.schedule.model.Request;
import com.fpt.edu.schedule.repository.base.QueryParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ReportService {
    void generateExcelFile(MultipartFile multipartFile, int semesterId);

    Request addReport(Request request, String lecturerId);

    Request updateReport(Request request);


    void removeReportById(int id);

    List<Request> findByCriteria(QueryParam queryParam);
}
