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
    TimetableDetailRepository timetableDetailRepository;
    TimetableRepository timetableRepository;
    SlotService slotService;
    ReportRepository reportRepository;
    LecturerRepository lecturerRepository;
    SemesterRepository semesterRepository;
    RoomRepository roomRepository;
    LecturerService lecturerService;

    @Override
    public void generateExcelFile(String fileName, int semesterId) {
        try {
            FileInputStream file = new FileInputStream(new File(fileName));
            XSSFWorkbook workbook = new XSSFWorkbook(file);
            XSSFSheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();
            rowIterator.next();
            Set<String> subjectNameSet = new HashSet<>();
            Set<String> roomNameSet = new HashSet<>();
            Set<String> classNameSet = new HashSet<>();
            Set<String> slotSet = new HashSet<>();
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
                            if (classNameRepository.findByName(cell.getStringCellValue().trim()) != null) {
                                break;
                            }
                            classNameSet.add(cell.getStringCellValue().trim());
                            break;
                        case 2:
                            if (subjectService.getSubjectByCode(cell.getStringCellValue().trim()) != null) {
                                break;
                            }
                            subjectNameSet.add(cell.getStringCellValue().trim());
                            break;
                        case 3:
                            if (slotService.getSlotByName(cell.getStringCellValue().trim()) != null) {
                                break;
                            }
                            slotSet.add(cell.getStringCellValue().trim());
                            break;
                        case 5:
                            if (roomRepository.findByName(cell.getStringCellValue().trim()) != null) {
                                break;
                            }
                            roomNameSet.add(cell.getStringCellValue().trim());
                            break;
                    }
                }
            }
            saveInformation(roomNameSet, classNameSet, subjectNameSet, slotSet);
            saveTimetable(fileName, semesterId);
            file.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Report addReport(Report report) {
        report.setCreatedDate(new Date());
        report.setStatus(Status.PENDING);
        Lecturer lecturer = lecturerService.getLecturerGoogleId(report.getLecturer().getGoogleId());
        return reportRepository.save(report);
    }

    @Override
    public Report updateReport(Report report) {
        Report existedReport = reportRepository.findReportById(report.getId());
        if (existedReport == null) {
            throw new InvalidRequestException("Don't find this report !");
        }
        existedReport.setContent(report.getContent() != null ? report.getContent() : existedReport.getContent());
        existedReport.setStatus(report.getStatus());
        existedReport.setReplyContent(report.getReplyContent());

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

    private void saveTimetable(String fileName, int semesterId) {
        try {
            FileInputStream file = new FileInputStream(new File(fileName));
            if (file == null) {
                throw new Exception("Not found this file!");
            }
            XSSFWorkbook workbook = new XSSFWorkbook(file);
            XSSFSheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();
            rowIterator.next();
            Timetable existedTimetable = timetableRepository.findBySemester(semesterRepository.findById(semesterId));

            if (existedTimetable != null) {
                timetableDetailRepository.deleteAllByTimetable(existedTimetable);
                timetableRepository.deleteById(existedTimetable.getId());
            }
            Timetable timeTable = new Timetable();
            while (rowIterator.hasNext()) {
                int column = 0;
                Row row = rowIterator.next();
                Iterator<Cell> cellIterator = row.cellIterator();
                if (!row.getCell(3).getStringCellValue().equalsIgnoreCase("CF")) {
                    continue;
                }
                TimetableDetail timetableDetail = new TimetableDetail();
                timeTable.setSemester(semesterRepository.findById(semesterId));
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
                            timetableDetail.setRoom(roomService.getRoomByName(cell.getStringCellValue().trim()));
                            break;
                    }
                    timetableDetail.setTimetable(timeTable);
                    timeTable.getTimetableDetails().add(timetableDetail);
                }
            }
            timetableRepository.save(timeTable);
            file.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
