package com.fpt.edu.schedule.service.base;

import com.fpt.edu.schedule.model.Request;
import com.fpt.edu.schedule.repository.base.QueryParam;
import org.springframework.web.multipart.MultipartFile;

public interface RequestService {
    void generateExcelFile(MultipartFile multipartFile, int semesterId);

    Request addRequest(Request request, String lecturerId);

    Request updateRequest(Request request);

    void removeRequestById(int id);

    QueryParam.PagedResultSet<Request> findByCriteria(QueryParam queryParam);
}
