package com.fpt.edu.schedule.service.base;

import com.fpt.edu.schedule.common.enums.Status;
import com.fpt.edu.schedule.model.UserName;
import com.fpt.edu.schedule.repository.base.QueryParam;

import java.util.List;

public interface UserService {

    UserName addUser(UserName user);

    List<UserName> findByCriteria(QueryParam queryParam);

    UserName getUserNameById(String id);

    UserName updateUserName(UserName userName);

    UserName updateStatus(Status status,String userId);

}
