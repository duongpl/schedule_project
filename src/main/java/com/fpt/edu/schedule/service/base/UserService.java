package com.fpt.edu.schedule.service.base;

import com.fpt.edu.schedule.model.UserName;
import com.fpt.edu.schedule.repository.impl.QueryParam;

import java.util.List;

public interface UserService {

    void addUser(UserName user);

    List<UserName> findByCriteria(QueryParam queryParam);

    UserName getUserNameById(String id);

}
