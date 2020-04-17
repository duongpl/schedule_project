package com.fpt.edu.schedule.service.impl;

import com.fpt.edu.schedule.common.enums.StatusReport;
import com.fpt.edu.schedule.common.exception.InvalidRequestException;
import com.fpt.edu.schedule.model.*;
import com.fpt.edu.schedule.repository.base.*;
import com.fpt.edu.schedule.service.base.*;
import lombok.AllArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Service
@AllArgsConstructor
public class RequestServiceImpl implements ReportService {
    RoomService roomService;
    ClassNameService classNameService;
    ClassNameRepository classNameRepository;
    SubjectService subjectService;
    TimeTableDetailService timeTableDetailService;
    TimetableDetailRepository timetableDetailRepository;
    TimetableRepository timetableRepository;
    SlotService slotService;
    RequestRepository requestRepository;
    LecturerRepository lecturerRepository;
    SemesterRepository semesterRepository;
    RoomRepository roomRepository;
    LecturerService lecturerService;
    DepartmentRepository departmentRepository;
    ExpectedRepository expectedRepository;

    @Transactional
    @Override
    public void generateExcelFile(MultipartFile multipartFile, int semesterId) {
        try {
            String extension = FilenameUtils.getExtension(multipartFile.getOriginalFilename());
            System.out.println(extension);
            if(!extension.contains("xlsx")){
                throw new InvalidRequestException("Wrong file format!");
            }
            XSSFWorkbook workbook = new XSSFWorkbook(multipartFile.getInputStream());
            XSSFSheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();
            rowIterator.next();
            while (rowIterator.hasNext()) {
                int column = 0;
                Row row = rowIterator.next();
                Iterator<Cell> cellIterator = row.cellIterator();
                while (cellIterator.hasNext()) {
                    column++;
                    Cell cell = cellIterator.next();
                    if (column == 6) {
                        break;
                    }
                    switch (column) {
                        case 1:
                            if (classNameRepository.findByName(cell.getStringCellValue().trim()) != null) {
                                break;
                            }
                            classNameService.addClassName(new ClassName(cell.getStringCellValue().trim()));
                            break;
                        case 2:
                            if (subjectService.getSubjectByCode(cell.getStringCellValue().trim()) != null) {
                                break;
                            }
                            subjectService.addSubject(new Subject(cell.getStringCellValue().trim(), row.getCell(3).getStringCellValue()));
                            break;
                        case 3:
                            if (slotService.getSlotByName(cell.getStringCellValue().trim()) != null) {
                                break;
                            }
                            slotService.addSlot(new Slot(cell.getStringCellValue().trim()));
                            break;
                        case 4:
                            if (departmentRepository.findByName(cell.getStringCellValue().trim()) != null) {
                                break;
                            }
                            departmentRepository.save(new Department(cell.getStringCellValue().trim()));
                            break;
                        case 5:
                            if (roomRepository.findByName(cell.getStringCellValue().trim()) != null) {
                                break;
                            }
                            roomService.addRoom(new Room(cell.getStringCellValue().trim()));
                            break;
                    }
                }
            }
            saveTimetable(multipartFile, semesterId);

        } catch (Exception e) {
            throw new InvalidRequestException(e.getMessage());
        }
    }

    @Override
    public Request addReport(Request request, String lecturerId) {
        request.setSemester(semesterRepository.getAllByNowIsTrue());
        request.setCreatedDate(new Date());
        request.setStatus(StatusReport.PENDING);
        Lecturer lecturer = lecturerService.findByGoogleId(lecturerId);
        request.setLecturer(lecturer);
        return requestRepository.save(request);
    }

    @Override
    public Request updateReport(Request request) {
        Request existedRequest = requestRepository.findReportById(request.getId());
        if (existedRequest == null) {
            throw new InvalidRequestException("Don't find this report !");
        }
        existedRequest.setStatus(request.getStatus());
        existedRequest.setReplyContent(request.getReplyContent());

        return requestRepository.save(existedRequest);
    }

    @Override
    public void removeReportById(int id) {
        Request existedRequest = requestRepository.findReportById(id);
        if (existedRequest == null) {
            throw new InvalidRequestException(String.format("Don't find this report reportId:%s", id));
        }
        requestRepository.removeById(id);
    }

    @Override
    public List<Request> findByCriteria(QueryParam queryParam) {
        BaseSpecifications cns = new BaseSpecifications(queryParam);
        return requestRepository.findAll(cns);
    }


    private void saveTimetable(MultipartFile multipartFile, int semesterId) {
        try {

            XSSFWorkbook workbook = new XSSFWorkbook(multipartFile.getInputStream());
            XSSFSheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();
            rowIterator.next();
            Timetable existedTimetable = timetableRepository.findBySemester(semesterRepository.findById(semesterId));

            if (existedTimetable != null) {
                timetableDetailRepository.deleteAllByTimetable(existedTimetable.getId());
                expectedRepository.deleteAllBySemester(semesterRepository.findById(semesterId));
                timetableRepository.deleteById(existedTimetable.getId());
            }
            Timetable timeTable = new Timetable();
            while (rowIterator.hasNext()) {
                int column = 0;
                Row row = rowIterator.next();
                Iterator<Cell> cellIterator = row.cellIterator();
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
