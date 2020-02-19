package com.fpt.edu.schedule.service.base;

import com.fpt.edu.schedule.model.Role;


public interface RoleService {
    Role getRoleByName(String name);

    void addRole(Role role);
}
