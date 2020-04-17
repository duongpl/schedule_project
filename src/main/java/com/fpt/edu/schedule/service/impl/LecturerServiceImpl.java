package com.fpt.edu.schedule.service.impl;


import com.fpt.edu.schedule.common.enums.Role;
import com.fpt.edu.schedule.common.enums.StatusLecturer;
import com.fpt.edu.schedule.common.exception.InvalidRequestException;
import com.fpt.edu.schedule.model.Lecturer;
import com.fpt.edu.schedule.model.Timetable;
import com.fpt.edu.schedule.model.TimetableDetail;
import com.fpt.edu.schedule.repository.base.*;
import com.fpt.edu.schedule.service.base.LecturerService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

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





    @Override
    public Lecturer addLecture(Lecturer lecturer,String hodGoogleId) {
        Lecturer newLecturer = new Lecturer();
        if (lecturerRepository.findByEmail(lecturer.getEmail()) != null) {
            throw new InvalidRequestException(String.format("Already have this lecturer %s ",lecturer.getEmail()));
        }
        Lecturer hod = findByGoogleId(hodGoogleId);
        newLecturer.setStatus(StatusLecturer.ACTIVATE);
        newLecturer.setEmail(lecturer.getEmail());
        newLecturer.setDepartment(hod.getDepartment());
        newLecturer.setShortName(newLecturer.getEmail().substring(0, newLecturer.getEmail().indexOf('@')));
        newLecturer.setRole(roleRepository.findByRoleName(Role.ROLE_USER.getName()));
        return lecturerRepository.save(newLecturer);
    }

    @Override
    public void remove(int id) {
        lecturerRepository.removeById(id);
    }

    @Override
    public List<Lecturer> findByCriteria(QueryParam queryParam) {
        BaseSpecifications cns = new BaseSpecifications(queryParam);
        List<Lecturer> lecturers = lecturerRepository.findAll(cns);
        for (Lecturer u : lecturers) {
            if (expectedRepository.findBySemesterAndLecturer(semesterRepository.getAllByNowIsTrue(),
                   lecturerRepository.findById(u.getId())) != null) {
                u.setFillingExpected(true);
            }
            if (u.getRole().getRoleName().equals(Role.ROLE_ADMIN.getName())) {
                u.setHeadOfDepartment(true);
            }

        }
        return lecturers;
    }

    @Override
    public Lecturer findByGoogleId(String id) {
        Lecturer lecturer = lecturerRepository.findByGoogleId(id);
        if (lecturer == null) {
            throw new InvalidRequestException("Don't find this lecturer");
        }
        return lecturer;
    }

    @Override
    public Lecturer updateLecturerName(Lecturer lecturer) {
        Lecturer existedUser = lecturerRepository.findById(lecturer.getId());
        if (existedUser == null) {
            throw new InvalidRequestException("Don't find this user !");
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
        Lecturer existedUser= findByGoogleId(lecturerGoogleId);
        Lecturer hod = findByGoogleId(hodGoogleId);
        if(!existedUser.getDepartment().equalsIgnoreCase(hod.getDepartment())){
            throw new InvalidRequestException("Don't have same department !");
        }
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
        if(status == StatusLecturer.DEACTIVATE) {
            List<TimetableDetail> timetableDetail = timetableDetailRepository.findAllByLecturerAndTimetable(lecturer,
                    timetableRepository.findBySemester(semesterRepository.getAllByNowIsTrue()));
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
    public List<Lecturer> findForUpdate(int timetableDetailId,QueryParam queryParam) {
        TimetableDetail timetableDetail = timetableDetailRepository.findById(timetableDetailId);
        Timetable timetable = timetableDetail.getTimetable();
        List<TimetableDetail> list = timetable.getTimetableDetails().stream().filter(i->
                i.getSlot().equals(timetableDetail.getSlot())).collect(Collectors.toList());
        List<Lecturer> lecturers = list.stream().map(TimetableDetail::getLecturer).collect(Collectors.toList());
        BaseSpecifications cns = new BaseSpecifications(queryParam);
        List<Lecturer> lecturer = (List<Lecturer>) lecturerRepository.findAll(cns).stream().filter(i->!lecturers.contains(i)).collect(Collectors.toList());

        return lecturer;
    }


}
