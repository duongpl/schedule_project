package com.fpt.edu.schedule.service.impl;


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
    public void addUser(UserName user) {
        userRepository.save(user);
    }

    @Override
    public List<UserName> findByCriteria(QueryParam queryParam) {
        BaseSpecifications cns = new BaseSpecifications(queryParam);

        return userRepository.findAll(cns);
    }
    @Override
    public UserName getUserNameById(String id) {
        return userRepository.findById(id);
    }
}
