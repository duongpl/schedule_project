package com.fpt.edu.schedule.service.impl;


import com.fpt.edu.schedule.common.enums.Status;
import com.fpt.edu.schedule.common.exception.InvalidRequestException;
import com.fpt.edu.schedule.model.UserName;
import com.fpt.edu.schedule.repository.base.BaseSpecifications;
import com.fpt.edu.schedule.repository.base.UserRepository;
import com.fpt.edu.schedule.repository.impl.QueryParam;
import com.fpt.edu.schedule.service.base.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    UserRepository userRepository;

    @Override
    public UserName addUser(UserName user) {
        return userRepository.save(user);
    }

    @Override
    public List<UserName> findByCriteria(QueryParam queryParam) {
        BaseSpecifications cns = new BaseSpecifications(queryParam);
        for(Object u : userRepository.findAll(cns)){
            if(u instanceof UserName){
                ((UserName) u).setFillingExpected(true);
                ((UserName) u).setHeadOfDepartment(true);

            }
        }
        return userRepository.findAll(cns);
    }
    @Override
    public UserName getUserNameById(String id) {
        return userRepository.findById(id);
    }

    @Override
    public UserName updateUserName(UserName userName) {
        UserName existedUser = userRepository.findById(userName.getId());
        if(existedUser == null){
            throw new InvalidRequestException("Don't find this user !");
        }
        existedUser.setFullName(userName.getFullName() != null ? userName.getFullName() : existedUser.getFullName());
        existedUser.setDepartment(userName.getDepartment() != null ? userName.getDepartment() : existedUser.getDepartment());
        existedUser.setPhone(userName.getPhone() != null ? userName.getPhone() : existedUser.getPhone());
        return userRepository.save(existedUser);
    }

    @Override
    public UserName updateStatus(Status status, String userId) {
        UserName existedUser=userRepository.findById(userId);
        if(existedUser == null){
            throw new InvalidRequestException("Don't find this user !");
        }
        existedUser.setStatus(status);
        return userRepository.save(existedUser);
    }


}
