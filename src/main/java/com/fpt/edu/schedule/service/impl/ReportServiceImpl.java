package com.fpt.edu.schedule.service.impl;

import com.fpt.edu.schedule.common.enums.Status;
import com.fpt.edu.schedule.common.exception.InvalidRequestException;
import com.fpt.edu.schedule.model.*;
import com.fpt.edu.schedule.repository.base.*;
import com.fpt.edu.schedule.service.base.*;
import lombok.AllArgsConstructor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.File;
import java.io.FileInputStream;
import java.util.*;

@Service
@Transactional
@AllArgsConstructor
public class ReportServiceImpl implements ReportService {
    RoomService roomService;
    ClassNameService classNameService;
    ClassNameRepository classNameRepository;
    SubjectService subjectService;
    TimeTableDetailService timeTableDetailService;
    SlotService slotService;
    ReportRepository reportRepository;
    LecturerRepository lecturerRepository;

    @Override
    public void generateExcelFile(String fileName) {
        try {
            FileInputStream file = new FileInputStream(new File(fileName));
            XSSFWorkbook workbook = new XSSFWorkbook(file);
            XSSFSheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();
            rowIterator.next();
            Set<String> subjectNameList = new HashSet<>();
            Set<String> roomNameList = new HashSet<>();
            Set<String> classNameList = new HashSet<>();
            Set<String> slotList = new HashSet<>();
            while (rowIterator.hasNext()) {
                int column = 0;
                Row row = rowIterator.next();
                Iterator<Cell> cellIterator = row.cellIterator();
                if (!row.getCell(3).getStringCellValue().equalsIgnoreCase("CF")) {
                    continue;
                }
                while (cellIterator.hasNext()) {
                    column++;
                    Cell cell = cellIterator.next();

                    if (cell.getCellTypeEnum().equals(CellType.BLANK)) {
                        break;
                    }
                    switch (column) {
                        case 1:
                            classNameList.add(cell.getStringCellValue().trim());
                            break;
                        case 2:
                            subjectNameList.add(cell.getStringCellValue().trim());
                            break;
                        case 3:
                            slotList.add(cell.getStringCellValue().trim());
                            break;
                        case 5:
                            roomNameList.add(cell.getStringCellValue().trim());
                            break;
                    }
                }
            }
            saveInformation(roomNameList, classNameList, subjectNameList, slotList);
            saveSchedule(fileName);
            file.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Report addReport(Report report) {
        report.setCreatedDate(new Date());
        report.setStatus(Status.PENDING);
        Lecturer lecturer = lecturerRepository.findById(report.getLecturer().getId());
        if (lecturer == null) {
            throw new InvalidRequestException("Don't find user!");
        }
        return reportRepository.save(report);
    }

    @Override
    public Report updateReport(Report report) {
        Report existedReport = reportRepository.findReportById(report.getId());
        if (existedReport == null) {
            throw new InvalidRequestException("Don't find this report !");
        }
        existedReport.setContent(report.getContent() != null ? report.getContent() : existedReport.getContent());
        return reportRepository.save(existedReport);
    }

    @Override
    public Report updateStatus(Status status, int reportId) {
        Report existedReport = reportRepository.findReportById(reportId);
        if (existedReport == null) {
            throw new InvalidRequestException("Don't find this report !");
        }
        existedReport.setStatus(status);
        return reportRepository.save(existedReport);
    }

    @Override
    public void removeReportById(int id) {
        Report existedReport = reportRepository.findReportById(id);
        if (existedReport == null) {
            throw new InvalidRequestException("Don't find this report");
        }
        reportRepository.removeById(id);
    }

    @Override
    public List<Report> findByCriteria(QueryParam queryParam) {
        BaseSpecifications cns = new BaseSpecifications(queryParam);
        return reportRepository.findAll(cns);
    }

    private void saveInformation(Set<String> roomSet, Set<String> classNameList, Set<String> subjectList, Set<String> slotList) {
        roomSet.forEach(i -> roomService.addRoom(new Room(i)));
        classNameList.forEach(i -> classNameService.addClassName(new ClassName(i)));
        subjectList.forEach(i -> subjectService.addSubject(new Subject(i, "CF")));
        slotList.forEach(i -> slotService.addSlot(new Slot(i)));
    }

    private void saveSchedule(String fileName) {
        try {
            FileInputStream file = new FileInputStream(new File(fileName));
            if (file == null) {
                throw new Exception("Not found this file!");
            }
            XSSFWorkbook workbook = new XSSFWorkbook(file);
            XSSFSheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();
            rowIterator.next();
            while (rowIterator.hasNext()) {
                int column = 0;
                Row row = rowIterator.next();
                Iterator<Cell> cellIterator = row.cellIterator();
                if (!row.getCell(3).getStringCellValue().equalsIgnoreCase("CF")) {
                    continue;
                }
                TimetableDetail timetableDetail = new TimetableDetail();
                while (cellIterator.hasNext()) {
                    column++;
                    Cell cell = cellIterator.next();

                    if (cell.getCellTypeEnum().equals(CellType.BLANK)) {
                        break;
                    }
                    switch (column) {
                        case 1:
                            timetableDetail.setClassName(classNameRepository.findByName(cell.getStringCellValue().trim()));
                            break;
                        case 2:
                            timetableDetail.setSubject(subjectService.getSubjectByCode(cell.getStringCellValue().trim()));
                            break;
                        case 3:
                            timetableDetail.setSlot(slotService.getSlotByName(cell.getStringCellValue().trim()));
                            break;
                        case 5:
                            timetableDetail.setRoom(roomService.getRoombyName(cell.getStringCellValue().trim()));
                            break;
                    }
                }
                timeTableDetailService.addTimeTableDetail(timetableDetail);
            }
            file.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
