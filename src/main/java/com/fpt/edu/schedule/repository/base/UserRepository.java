package com.fpt.edu.schedule.repository.base;

import com.fpt.edu.schedule.model.UserName;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface UserRepository extends Repository<UserName,String>,JpaSpecificationExecutor<UserName> {

    UserName save(UserName user);

    UserName findById(String id);

}
