package com.fpt.edu.schedule.service.base;

import com.fpt.edu.schedule.model.UserName;

import java.util.List;

public interface UserService {

    void addUser(UserName user);

    List<UserName> getAllUser();

    UserName getUserNameById(String id);

}
