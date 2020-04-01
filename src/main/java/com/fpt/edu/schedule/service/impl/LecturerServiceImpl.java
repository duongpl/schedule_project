package com.fpt.edu.schedule.service.impl;


import com.fpt.edu.schedule.common.enums.Role;
import com.fpt.edu.schedule.common.enums.Status;
import com.fpt.edu.schedule.common.exception.InvalidRequestException;
import com.fpt.edu.schedule.model.Lecturer;
import com.fpt.edu.schedule.repository.base.*;
import com.fpt.edu.schedule.service.base.LecturerService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class LecturerServiceImpl implements LecturerService {
    LecturerRepository lecturerRepository;
    RoleRepository roleRepository;
    SemesterRepository semesterRepository;
    ExpectedRepository expectedRepository;


    @Override
    public Lecturer addLecture(Lecturer lecturer,String hodGoogleId) {
        Lecturer newLecturer = new Lecturer();
        if (lecturerRepository.findByEmail(lecturer.getEmail()) != null) {
            throw new InvalidRequestException("Already have this lecturer");
        }
        Lecturer hod = findByGoogleId(hodGoogleId);

        newLecturer.setEmail(lecturer.getEmail());
        newLecturer.setDepartment(hod.getDepartment());
        newLecturer.setShortName(newLecturer.getEmail().substring(0, newLecturer.getEmail().indexOf('@')));
        newLecturer.setRole(roleRepository.findByRoleName(Role.ROLE_USER.getName()));
        return lecturerRepository.save(newLecturer);
    }

    @Override
    public void remove(String id) {
        lecturerRepository.removeByGoogleId(id);
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
    public Lecturer updateStatus(Status status, String userId) {
//        Lecturer existedUser= lecturerRepository.findById(userId);
//        if(existedUser == null){
//            throw new InvalidRequestException("Don't find this user !");
//        }
//        existedUser.setStatus(status);
//        return lecturerRepository.save(existedUser);
        return null;
    }

    @Override
    public Lecturer findByShortName(String shortName) {
        Lecturer lecturer = lecturerRepository.findByShortName(shortName);
        if (lecturer == null) {
            throw new InvalidRequestException("Don't find this lecturer");
        }
        return lecturer;
    }


}
