package com.fpt.edu.schedule.service.impl;


import com.fpt.edu.schedule.common.constant.MessageResponse;
import com.fpt.edu.schedule.common.enums.Role;
import com.fpt.edu.schedule.common.enums.StatusLecturer;
import com.fpt.edu.schedule.common.enums.TimetableStatus;
import com.fpt.edu.schedule.common.exception.InvalidRequestException;
import com.fpt.edu.schedule.dto.TimetableProcess;
import com.fpt.edu.schedule.model.*;
import com.fpt.edu.schedule.repository.base.*;
import com.fpt.edu.schedule.service.base.LecturerService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class LecturerServiceImpl implements LecturerService {
    LecturerRepository lecturerRepo;
    RoleRepository roleRepo;
    SemesterRepository semesterRepo;
    ExpectedRepository expectedRepo;
    TimetableDetailRepository timetableDetailRepo;
    TimetableRepository timetableRepo;
    ConfirmationRepository confirmationRe;

    @Autowired
    TimetableProcess timetableProcess;

    @Override
    public Lecturer addLecture(Lecturer lecturer, String hodGoogleId) {
        Lecturer newLecturer = new Lecturer();
        if (lecturerRepo.findByEmail(lecturer.getEmail()) != null) {
            throw new InvalidRequestException(String.format(MessageResponse.msgAlreadyHaveEmail, lecturer.getEmail()));
        }
        Lecturer hod = findByGoogleId(hodGoogleId);
        newLecturer.setStatus(StatusLecturer.ACTIVATE);
        newLecturer.setEmail(lecturer.getEmail());
        newLecturer.setDepartment(hod.getDepartment());
        newLecturer.setShortName(newLecturer.getEmail().substring(0, newLecturer.getEmail().indexOf('@')));
        newLecturer.setRole(roleRepo.findByRoleName(Role.ROLE_USER.getName()));
        return lecturerRepo.save(newLecturer);
    }

    @Transactional
    @Override
    public void remove(int id) {
        Lecturer lec = lecturerRepo.findById(id);
        if (lec.isLogin()) {
            timetableDetailRepo.deleteByLecturer(id);
        }
        lecturerRepo.removeById(id);
    }

    @Override
    public QueryParam.PagedResultSet<Lecturer> findByCriteria(QueryParam queryParam, int semesterId) {
        QueryParam.PagedResultSet page = new QueryParam.PagedResultSet();
        BaseSpecifications cns = new BaseSpecifications(queryParam);
        page.setTotalCount((int) lecturerRepo.count(cns));
        if (queryParam.getPage() < 1) {
            queryParam.setPage(1);
            queryParam.setLimit(1000);
        }
        Page<Lecturer> lecturers = lecturerRepo.findAll(cns, PageRequest.of(queryParam.getPage() - 1
                , queryParam.getLimit()));
        if (queryParam.getSortField() != null) {
            lecturers = lecturerRepo.findAll(cns, PageRequest.of(queryParam.getPage() - 1
                    , queryParam.getLimit(), Sort.by(queryParam.isAscending() ? Sort.Direction.ASC : Sort.Direction.DESC, queryParam.getSortField())));
        }
        if (semesterId != 0) {
            System.out.println("ok");
        }
        page.setPage(queryParam.getPage());
        page.setLimit(queryParam.getLimit());
        page.setSize(lecturers.getContent().size());
        page.setResults(lecturers.getContent());

        for (Lecturer u : lecturers) {
            if (semesterId != 0 ){
                Confirmation con = confirmationRe.findBySemesterAndLecturer(semesterRepo.findById(semesterId),u);
                u.setTimetableStatus(con !=null ? con.getStatus() : TimetableStatus.DRAFT);
            }
            if (expectedRepo.findBySemesterAndLecturer(semesterRepo.getAllByNowIsTrue(),
                    lecturerRepo.findById(u.getId())) != null) {
                u.setFillingExpected(true);
            }
            if (u.getRole().getRoleName().equals(Role.ROLE_ADMIN.getName())) {
                u.setHeadOfDepartment(true);
            }
        }
        return page;
    }

    @Override
    public Lecturer findByGoogleId(String id) {
        Lecturer lecturer = lecturerRepo.findByGoogleId(id);
        if (lecturer == null) {
            throw new InvalidRequestException("Don't find this lecturer: " + lecturer.getEmail());
        }
        return lecturer;
    }

    @Override
    public Lecturer updateLecturerName(Lecturer lecturer) {
        Lecturer existedUser = lecturerRepo.findById(lecturer.getId());
        if (existedUser == null) {
            throw new InvalidRequestException("Don't find this user!");
        }
        Lecturer checkDupShortName = lecturerRepo.findByShortName(lecturer.getShortName());
        if (checkDupShortName != null && !lecturer.getShortName().equalsIgnoreCase(existedUser.getShortName())) {
            throw new InvalidRequestException("Already have this short name:" + checkDupShortName.getShortName() + "!");
        }
        existedUser.setFullName(lecturer.getFullName() != null ? lecturer.getFullName() : existedUser.getFullName());
        existedUser.setDepartment(lecturer.getDepartment() != null ? lecturer.getDepartment() : existedUser.getDepartment());
        existedUser.setPhone(lecturer.getPhone() != null ? lecturer.getPhone() : existedUser.getPhone());
        existedUser.setShortName(lecturer.getShortName() != null ? lecturer.getShortName() : existedUser.getShortName());
        existedUser.setFullTime(lecturer.isFullTime());
        existedUser.setQuotaClass(lecturer.getQuotaClass() != 0 ? lecturer.getQuotaClass() : existedUser.getQuotaClass());
        return lecturerRepo.save(existedUser);
    }

    @Override
    public Lecturer transferRole(String hodGoogleId, String lecturerGoogleId) {
        Lecturer existedUser = findByGoogleId(lecturerGoogleId);

        Lecturer hod = findByGoogleId(hodGoogleId);
        if (!existedUser.getDepartment().equalsIgnoreCase(hod.getDepartment())) {
            throw new InvalidRequestException("Don't have same department !");
        }
        timetableProcess.getMap().remove(hodGoogleId);
        existedUser.setRole(roleRepo.findByRoleName(Role.ROLE_ADMIN.getName()));
        hod.setRole(roleRepo.findByRoleName(Role.ROLE_USER.getName()));
        lecturerRepo.save(hod);
        return lecturerRepo.save(existedUser);
    }


    @Override
    public Lecturer findByShortName(String shortName) {
        Lecturer lecturer = lecturerRepo.findByShortName(shortName);
        if (lecturer == null) {
            throw new InvalidRequestException("Don't find this lecturer");
        }
        return lecturer;
    }

    @Override
    public Lecturer findById(int id) {
        Lecturer lecturer = lecturerRepo.findById(id);
        if (lecturer == null) {
            throw new InvalidRequestException(String.format("Not found lecturerId:%d", id));
        }
        return lecturer;
    }

    @Override
    public Lecturer changeStatus(StatusLecturer status, String googleId) {
        Lecturer lecturer = findByGoogleId(googleId);
        if (status == StatusLecturer.DEACTIVATE) {
            List<TimetableDetail> timetableDetail = timetableDetailRepo.findAllByLecturerAndTimetable(lecturer,
                    timetableRepo.findBySemesterAndTempFalse(semesterRepo.getAllByNowIsTrue()));
            Confirmation confirmation = confirmationRe.findBySemesterAndLecturer(semesterRepo.getAllByNowIsTrue(),lecturer);
            if(confirmation !=null) {
                confirmationRe.deleteById(confirmation.getId());
            }
            // remove all timetable of this lecture
            timetableDetail.stream().forEach(i -> {
                i.setLecturer(null);
                timetableDetailRepo.save(i);
            });
        }
        lecturer.setStatus(status);
        return lecturerRepo.save(lecturer);
    }

    @Override
    public List<Lecturer> findForUpdate(int timetableDetailId, QueryParam queryParam) {
        TimetableDetail timetableDetail = timetableDetailRepo.findById(timetableDetailId);
        Semester semester = timetableDetail.getTimetable().getSemester();
        Timetable timetable = timetableDetail.getTimetable();
        List<TimetableDetail> list = timetable
                .getTimetableDetails()
                .stream()
                .filter(i -> i.getSlot().equals(timetableDetail.getSlot()))
                .collect(Collectors.toList());
        List<Lecturer> lecturers = list
                .stream()
                .filter(i -> i.getLecturer() != null)
                .map(TimetableDetail::getLecturer)
                .collect(Collectors.toList());
        BaseSpecifications cns = new BaseSpecifications(queryParam);
        if (isOnlineTimetableDetail(timetableDetail)) {
            return lecturerRepo.findAll(cns);
        }
        List<Lecturer> lecturer = (List<Lecturer>) lecturerRepo
                .findAll(cns)
                .stream()
                .filter(i -> !lecturers.contains(i))
                .collect(Collectors.toList());
        lecturer.stream().forEach(u-> {
            Confirmation con = confirmationRe.findBySemesterAndLecturer(semester, u);
            u.setTimetableStatus(con != null ? con.getStatus() : TimetableStatus.DRAFT);
        });
        return lecturer;
    }

    boolean isOnlineTimetableDetail(TimetableDetail timetableDetail) {
        return Character
                .isAlphabetic(timetableDetail.getSubject().getCode()
                        .charAt(timetableDetail.getSubject().getCode().length() - 1));
    }
}
