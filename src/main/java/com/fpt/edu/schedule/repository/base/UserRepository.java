package com.fpt.edu.schedule.repository.base;

import com.fpt.edu.schedule.model.UserName;
import org.springframework.data.repository.Repository;

public interface UserRepository extends Repository<UserName,String> {

    void save(UserName user);

    UserName findById(String id);
}
