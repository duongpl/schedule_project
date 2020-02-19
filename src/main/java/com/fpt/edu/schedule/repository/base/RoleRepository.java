package com.fpt.edu.schedule.repository.base;

import com.fpt.edu.schedule.model.Role;
import org.springframework.data.repository.Repository;


public interface RoleRepository extends Repository<Role,Integer> {
    Role findByRoleName(String name);

    void save (Role role);
}
