package com.fpt.edu.schedule.service.impl;


import com.fpt.edu.schedule.common.enums.Status;
import com.fpt.edu.schedule.common.exception.InvalidRequestException;
import com.fpt.edu.schedule.model.Lecturer;
import com.fpt.edu.schedule.repository.base.BaseSpecifications;
import com.fpt.edu.schedule.repository.base.LecturerRepository;
import com.fpt.edu.schedule.repository.base.QueryParam;
import com.fpt.edu.schedule.service.base.LecturerService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@AllArgsConstructor
@Service
public class LecturerServiceImpl implements LecturerService {
    LecturerRepository lecturerRepository;

    @Override
    public Lecturer addUser(Lecturer user) {
        return lecturerRepository.save(user);
    }

    @Override
    public List<Lecturer> findByCriteria(QueryParam queryParam) {
        BaseSpecifications cns = new BaseSpecifications(queryParam);
        for(Object u : lecturerRepository.findAll(cns)){
            if(u instanceof Lecturer){
                ((Lecturer) u).setFillingExpected(true);
                ((Lecturer) u).setHeadOfDepartment(true);

            }
        }
        return lecturerRepository.findAll(cns);
    }
    @Override
    public Lecturer getLecturerNameById(String id) {
        return lecturerRepository.findById(id);
    }

    @Override
    public Lecturer updateLecturerName(Lecturer lecturer) {
        Lecturer existedUser = lecturerRepository.findById(lecturer.getId());
        if(existedUser == null){
            throw new InvalidRequestException("Don't find this user !");
        }
        existedUser.setFullName(lecturer.getFullName() != null ? lecturer.getFullName() : existedUser.getFullName());
        existedUser.setDepartment(lecturer.getDepartment() != null ? lecturer.getDepartment() : existedUser.getDepartment());
        existedUser.setPhone(lecturer.getPhone() != null ? lecturer.getPhone() : existedUser.getPhone());
        return lecturerRepository.save(existedUser);
    }

    @Override
    public Lecturer updateStatus(Status status, String userId) {
        Lecturer existedUser= lecturerRepository.findById(userId);
        if(existedUser == null){
            throw new InvalidRequestException("Don't find this user !");
        }
        existedUser.setStatus(status);
        return lecturerRepository.save(existedUser);
    }


}