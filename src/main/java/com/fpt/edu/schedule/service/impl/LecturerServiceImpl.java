package com.fpt.edu.schedule.service.impl;


import com.fpt.edu.schedule.common.constant.MessageResponse;
import com.fpt.edu.schedule.common.enums.Role;
import com.fpt.edu.schedule.common.enums.StatusLecturer;
import com.fpt.edu.schedule.common.exception.InvalidRequestException;
import com.fpt.edu.schedule.dto.TimetableProcess;
import com.fpt.edu.schedule.model.Lecturer;
import com.fpt.edu.schedule.model.Timetable;
import com.fpt.edu.schedule.model.TimetableDetail;
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
    LecturerRepository lecturerRepository;
    RoleRepository roleRepository;
    SemesterRepository semesterRepository;
    ExpectedRepository expectedRepository;
    TimetableDetailRepository timetableDetailRepository;
    TimetableRepository timetableRepository;

    @Autowired
    TimetableProcess timetableProcess;

    @Override
    public Lecturer addLecture(Lecturer lecturer, String hodGoogleId) {
        Lecturer newLecturer = new Lecturer();
        if (lecturerRepository.findByEmail(lecturer.getEmail()) != null) {
            throw new InvalidRequestException(String.format(MessageResponse.msgAlreadyHaveEmail, lecturer.getEmail()));
        }
        Lecturer hod = findByGoogleId(hodGoogleId);
        newLecturer.setStatus(StatusLecturer.ACTIVATE);
        newLecturer.setEmail(lecturer.getEmail());
        newLecturer.setDepartment(hod.getDepartment());
        newLecturer.setShortName(newLecturer.getEmail().substring(0, newLecturer.getEmail().indexOf('@')));
        newLecturer.setRole(roleRepository.findByRoleName(Role.ROLE_USER.getName()));
        return lecturerRepository.save(newLecturer);
    }

    @Transactional
    @Override
    public void remove(int id) {
        Lecturer lec = lecturerRepository.findById(id);
        if(lec.isLogin()){
            timetableDetailRepository.deleteByLecturer(id);
        }
        lecturerRepository.removeById(id);
    }

    @Override
    public QueryParam.PagedResultSet<Lecturer> findByCriteria(QueryParam queryParam) {
        QueryParam.PagedResultSet page = new QueryParam.PagedResultSet();
        BaseSpecifications cns = new BaseSpecifications(queryParam);
        page.setTotalCount((int) lecturerRepository.count(cns));
        if (queryParam.getPage() < 1) {
            queryParam.setPage(1);
            queryParam.setLimit(1000);
        }
        Page<Lecturer> lecturers = lecturerRepository.findAll(cns, PageRequest.of(queryParam.getPage() - 1
                , queryParam.getLimit()));
        if (queryParam.getSortField() != null) {
            lecturers = lecturerRepository.findAll(cns, PageRequest.of(queryParam.getPage() - 1
                    , queryParam.getLimit(), Sort.by(queryParam.isAscending() ? Sort.Direction.ASC : Sort.Direction.DESC, queryParam.getSortField())));
        }
        page.setPage(queryParam.getPage());
        page.setLimit(queryParam.getLimit());
        page.setSize(lecturers.getContent().size());
        page.setResults(lecturers.getContent());
        for (Lecturer u : lecturers) {
            if (expectedRepository.findBySemesterAndLecturer(semesterRepository.getAllByNowIsTrue(),
                    lecturerRepository.findById(u.getId())) != null) {
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
        Lecturer lecturer = lecturerRepository.findByGoogleId(id);
        if (lecturer == null) {
            throw new InvalidRequestException("Don't find this lecturer: "+lecturer.getEmail());
        }
        return lecturer;
    }

    @Override
    public Lecturer updateLecturerName(Lecturer lecturer) {
        Lecturer existedUser = lecturerRepository.findById(lecturer.getId());
        if (existedUser == null) {
            throw new InvalidRequestException("Don't find this user!");
        }
        Lecturer checkDupShortName=lecturerRepository.findByShortName(lecturer.getShortName());
        if(checkDupShortName!=null && !lecturer.getShortName().equalsIgnoreCase(existedUser.getShortName())){
            throw new InvalidRequestException("Already have this short name:"+checkDupShortName.getShortName()+"!");
        }
        existedUser.setFullName(lecturer.getFullName() != null ? lecturer.getFullName() : existedUser.getFullName());
        existedUser.setDepartment(lecturer.getDepartment() != null ? lecturer.getDepartment() : existedUser.getDepartment());
        existedUser.setPhone(lecturer.getPhone() != null ? lecturer.getPhone() : existedUser.getPhone());
        existedUser.setShortName(lecturer.getShortName() != null ? lecturer.getShortName() : existedUser.getShortName());
        existedUser.setFullTime(lecturer.isFullTime());
        existedUser.setQuotaClass(lecturer.getQuotaClass() != 0 ? lecturer.getQuotaClass() : existedUser.getQuotaClass());
        return lecturerRepository.save(existedUser);
    }

    @Override
    public Lecturer transferRole(String hodGoogleId, String lecturerGoogleId) {
        Lecturer existedUser = findByGoogleId(lecturerGoogleId);

        Lecturer hod = findByGoogleId(hodGoogleId);
        if (!existedUser.getDepartment().equalsIgnoreCase(hod.getDepartment())) {
            throw new InvalidRequestException("Don't have same department !");
        }
        timetableProcess.getMap().remove(hodGoogleId);
        existedUser.setRole(roleRepository.findByRoleName(Role.ROLE_ADMIN.getName()));
        hod.setRole(roleRepository.findByRoleName(Role.ROLE_USER.getName()));
        lecturerRepository.save(hod);
        return lecturerRepository.save(existedUser);
    }


    @Override
    public Lecturer findByShortName(String shortName) {
        Lecturer lecturer = lecturerRepository.findByShortName(shortName);
        if (lecturer == null) {
            throw new InvalidRequestException("Don't find this lecturer");
        }
        return lecturer;
    }

    @Override
    public Lecturer changeStatus(StatusLecturer status, String googleId) {
        Lecturer lecturer = findByGoogleId(googleId);
        if (status == StatusLecturer.DEACTIVATE) {
            List<TimetableDetail> timetableDetail = timetableDetailRepository.findAllByLecturerAndTimetable(lecturer,
                    timetableRepository.findBySemesterAndTempFalse(semesterRepository.getAllByNowIsTrue()));
            // remove all timetable of this lecture
            timetableDetail.stream().forEach(i -> {
                i.setLecturer(null);
                timetableDetailRepository.save(i);
            });
        }
        lecturer.setStatus(status);
        return lecturerRepository.save(lecturer);
    }

    @Override
    public List<Lecturer> findForUpdate(int timetableDetailId, QueryParam queryParam) {
        TimetableDetail timetableDetail = timetableDetailRepository.findById(timetableDetailId);
        Timetable timetable = timetableDetail.getTimetable();
        List<TimetableDetail> list = timetable
                .getTimetableDetails()
                .stream()
                .filter(i ->
                i.getSlot().equals(timetableDetail.getSlot()))
                .collect(Collectors.toList());
        List<Lecturer> lecturers = list
                .stream()
                .filter(i -> i.getLecturer() != null)
                .map(TimetableDetail::getLecturer)
                .collect(Collectors.toList());
        if (isOnlineTimetableDetail(timetableDetail)){
            return lecturers;
        }
        BaseSpecifications cns = new BaseSpecifications(queryParam);
        List<Lecturer> lecturer = (List<Lecturer>) lecturerRepository
                .findAll(cns)
                .stream()
                .filter(i -> !lecturers.contains(i))
                .collect(Collectors.toList());

        return lecturer;
    }
    boolean isOnlineTimetableDetail(TimetableDetail timetableDetail){
        return Character
                .isAlphabetic(timetableDetail.getSubject().getCode()
                        .charAt(timetableDetail.getSubject().getCode().length() - 1));
    }
}
