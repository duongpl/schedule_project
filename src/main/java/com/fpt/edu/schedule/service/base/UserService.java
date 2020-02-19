package com.fpt.edu.schedule.service.base;

import com.fpt.edu.schedule.model.UserName;

import java.util.List;

public interface UserService {
    List<UserName> getAllUser();

    UserName getUserNameById(String id);

}
