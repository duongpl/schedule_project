package com.fpt.edu.schedule.service.impl;


import com.fpt.edu.schedule.common.enums.Role;
import com.fpt.edu.schedule.common.enums.Status;
import com.fpt.edu.schedule.common.exception.InvalidRequestException;
import com.fpt.edu.schedule.model.Lecturer;
import com.fpt.edu.schedule.repository.base.BaseSpecifications;
import com.fpt.edu.schedule.repository.base.LecturerRepository;
import com.fpt.edu.schedule.repository.base.QueryParam;
import com.fpt.edu.schedule.repository.base.RoleRepository;
import com.fpt.edu.schedule.service.base.LecturerService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@AllArgsConstructor
@Service
public class LecturerServiceImpl implements LecturerService {
    LecturerRepository lecturerRepository;
    RoleRepository roleRepository;

    @Override
    public Lecturer addLecture(Lecturer lecturer) {
        Lecturer newLecturer = new Lecturer();
        newLecturer.setEmail(lecturer.getEmail());
        newLecturer.setShortName(lecturer.getEmail().substring(0, lecturer.getEmail().indexOf('@')));
        newLecturer.setRole(roleRepository.findByRoleName(Role.ROLE_ADMIN.getName()));;
        return lecturerRepository.save(lecturer);
    }

    @Override
    public void remove(String id) {
        lecturerRepository.removeByGoogleId(id);
    }

    @Override
    public List<Lecturer> findByCriteria(QueryParam queryParam) {
        BaseSpecifications cns = new BaseSpecifications(queryParam);
        for(Object u : lecturerRepository.findAll(cns)){
            if(u instanceof Lecturer){
                if(1 == 1) {
                    ((Lecturer) u).setFillingExpected(true);
                }
                if(((Lecturer) u).getRole().getRoleName().equals(Role.ROLE_ADMIN.getName())) {
                    ((Lecturer) u).setHeadOfDepartment(true);
                }
            }
        }
        return lecturerRepository.findAll(cns);
    }
    @Override
    public Lecturer getLecturerGoogleId(String id) {
        Lecturer lecturer =lecturerRepository.findByGoogleId(id);
        if(lecturer == null){
            throw new  InvalidRequestException("Don't find this lecturer");
        }
        return lecturer;
    }

    @Override
    public Lecturer updateLecturerName(Lecturer lecturer) {
        Lecturer existedUser = lecturerRepository.findByGoogleId(lecturer.getGoogleId());
        if(existedUser == null){
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
        Lecturer lecturer =lecturerRepository.findByShortName(shortName);
        if(lecturer == null){
            throw new  InvalidRequestException("Don't find this lecturer");
        }
        return lecturer;
    }


}
