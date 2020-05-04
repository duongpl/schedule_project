package com.fpt.edu.schedule.service.impl;

import com.fpt.edu.schedule.common.constant.Config;
import com.fpt.edu.schedule.common.enums.Role;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Service
@AllArgsConstructor
public class RequestServiceImpl implements RequestService {
    RoomService roomService;
    ClassNameService classNameService;
    ClassNameRepository classNameRepository;
    SubjectService subjectService;
    TimeTableDetailService timeTableDetailService;
    TimetableDetailRepository timetableDetailRepo;
    TimetableRepository timetableRepo;
    SlotService slotService;
    RequestRepository requestRepository;
    LecturerRepository lecturerRepo;
    SemesterRepository semesterRepo;
    RoomRepository roomRepo;
    LecturerService lecturerService;
    DepartmentRepository departmentRepository;
    ExpectedRepository expectedRepo;
    RoleService roleService;
    ConfirmationRepository confirmationRepos;
    @Autowired
    private JavaMailSender javaMailSender;

    @Transactional
    @Override
    public void generateExcelFile(MultipartFile multipartFile, int semesterId,String hodGoogleId) {
        try {
            Lecturer lecturer = lecturerService.findByGoogleId(hodGoogleId);
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
                if (isSkip(row,lecturer)) {
                    continue;
                }
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
                            if (roomRepo.findByName(cell.getStringCellValue().trim()) != null) {
                                break;
                            }
                            roomService.addRoom(new Room(cell.getStringCellValue().trim()));
                            break;
                    }
                }
            }
            saveTimetable(multipartFile, semesterId,lecturer);

        } catch (Exception e) {
            throw new InvalidRequestException(e.getMessage());
        }
    }

    @Override
    public Request addRequest(Request request, String lecturerId) {

        request.setSemester(semesterRepo.getAllByNowIsTrue());
        request.setCreatedDate(new Date());
        request.setStatus(StatusReport.PENDING);
        Lecturer lecturer = lecturerService.findByGoogleId(lecturerId);
        Lecturer hod = lecturerRepo.findAllByDepartmentAndRole(lecturer.getDepartment(),roleService.getRoleByName(Role.ROLE_ADMIN.getName()));
        request.setLecturer(lecturer);
        String title = "[DSST SYSTEM] New Request";
        String content = String.format("Lecturer: %s \nRequest: %s\n\n\nPlease visit %s to response !",lecturer.getEmail(),request.getContent(),Config.domainWebsite);
        sendEmail(content,hod.getEmail(),title);
        return requestRepository.save(request);
    }
    @Override
    public Request updateRequest(Request request,String lecturerId) {
        Lecturer hod = lecturerService.findByGoogleId(lecturerId);
        Request existedRequest = requestRepository.findReportById(request.getId());
        if (existedRequest == null) {
            throw new InvalidRequestException("Don't find this report !");
        }
        existedRequest.setStatus(request.getStatus());
        existedRequest.setReplyContent(request.getReplyContent());
        String content = String.format("Lecturer: %s response your request: %s\nReply: %s\nStatus: %s\n\nPlease visit %s to response !"
                ,hod.getEmail(),existedRequest.getContent(),existedRequest.getReplyContent(),existedRequest.getStatus(),Config.domainWebsite);
        String title = "[DSST SYSTEM] Response Request";
        sendEmail(content,existedRequest.getLecturer().getEmail(),title);
        return requestRepository.save(existedRequest);
    }

    @Override
    public void removeRequestById(int id) {
        Request existedRequest = requestRepository.findReportById(id);
        if (existedRequest == null) {
            throw new InvalidRequestException(String.format("Don't find this report reportId:%s", id));
        }
        requestRepository.removeById(id);
    }

    @Override
    public QueryParam.PagedResultSet<Request> findByCriteria(QueryParam queryParam) {
        QueryParam.PagedResultSet page = new QueryParam.PagedResultSet();
        BaseSpecifications cns = new BaseSpecifications(queryParam);
        page.setTotalCount((int)requestRepository.count(cns));
        if(queryParam.getPage() < 1){
            queryParam.setPage(1);
            queryParam.setLimit(1000);
        }
        Page<Lecturer> requests = requestRepository.findAll(cns, PageRequest.of(queryParam.getPage()-1, queryParam.getLimit()
                , Sort.by(queryParam.isAscending() ? Sort.Direction.ASC : Sort.Direction.DESC,queryParam.getSortField())));
        page.setPage(queryParam.getPage());
        page.setLimit(queryParam.getLimit());
        page.setSize(requests.getContent().size());
        page.setResults(requests.getContent());
        return page;
    }


     void saveTimetable(MultipartFile multipartFile, int semesterId,Lecturer lecturer) {
        try {
            Semester se = semesterRepo.findById(semesterId);
            XSSFWorkbook workbook = new XSSFWorkbook(multipartFile.getInputStream());
            XSSFSheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();
            rowIterator.next();
            List<Timetable> existedTimetable = timetableRepo.findAllBySemester(se);

            if (existedTimetable.size()>0) {

                existedTimetable.stream().forEach(i->{
                    timetableDetailRepo.deleteAllByTimetable(i.getId());
                    timetableRepo.deleteById(i.getId());
                });
                confirmationRepos.deleteAllBySemester(se);
                expectedRepo.deleteAllBySemester(se);

            }
            Timetable timeTable = new Timetable();
            Timetable timeTableTemp = new Timetable();
            timeTableTemp.setTemp(true);
            int line = 0;
            int lineTemp =0;
            while (rowIterator.hasNext()) {
                int column = 0;

                Row row = rowIterator.next();
                if (isSkip(row,lecturer)) {
                    continue;
                }
                Iterator<Cell> cellIterator = row.cellIterator();
                TimetableDetail timetableDetail = new TimetableDetail();
                TimetableDetail timetableDetailTemp = new TimetableDetail();
                timeTable.setSemester(semesterRepo.findById(semesterId));
                timeTableTemp.setSemester(semesterRepo.findById(semesterId));
                line++;
                lineTemp++;
                while (cellIterator.hasNext()) {
                    column++;
                    Cell cell = cellIterator.next();

                    if (cell.getCellTypeEnum().equals(CellType.BLANK)) {
                        break;
                    }
                    switch (column) {
                        case 1:
                            timetableDetail.setClassName(classNameRepository.findByName(cell.getStringCellValue().trim()));
                            timetableDetailTemp.setClassName(classNameRepository.findByName(cell.getStringCellValue().trim()));

                            break;
                        case 2:
                            timetableDetail.setSubject(subjectService.getSubjectByCode(cell.getStringCellValue().trim()));
                            timetableDetailTemp.setSubject(subjectService.getSubjectByCode(cell.getStringCellValue().trim()));

                            break;
                        case 3:
                            timetableDetail.setSlot(slotService.getSlotByName(cell.getStringCellValue().trim()));
                            timetableDetailTemp.setSlot(slotService.getSlotByName(cell.getStringCellValue().trim()));

                            break;
                        case 5:
                            timetableDetail.setRoom(roomService.getRoomByName(cell.getStringCellValue().trim()));
                            timetableDetailTemp.setRoom(roomService.getRoomByName(cell.getStringCellValue().trim()));
                            break;
                    }
                    timetableDetail.setTimetable(timeTable);
                    timetableDetail.setLineId(line);
                    timeTable.getTimetableDetails().add(timetableDetail);


                    timetableDetailTemp.setTimetable(timeTableTemp);
                    timetableDetailTemp.setLineId(lineTemp);
                    timeTableTemp.getTimetableDetails().add(timetableDetailTemp);
                }
            }
            timetableRepo.save(timeTableTemp);
            timetableRepo.save(timeTable);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private boolean isSkip(Row row,Lecturer lecturer){
        Cell cell = row.getCell(6);
        if (!row.getCell(3).getStringCellValue().equalsIgnoreCase(lecturer.getDepartment()) ||cell!= null ) {
            return true;
        }
        return false;
    }
    @Async
    void sendEmail(String content,String receiveEmail,String subject) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(receiveEmail);
        msg.setSubject(subject);
        msg.setText(content);
        javaMailSender.send(msg);

    }
}
