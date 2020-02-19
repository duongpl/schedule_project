package com.fpt.edu.schedule.service.impl;

import com.fpt.edu.schedule.model.Role;
import com.fpt.edu.schedule.repository.base.RoleRepository;
import com.fpt.edu.schedule.service.base.RoleService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class RoleServiceImpl implements RoleService {
    RoleRepository roleRepository;
    @Override
    public Role getRoleByName(String name) {
        return roleRepository.findByRoleName(name);
    }

    @Override
    public void addRole(Role role) {
        roleRepository.save(role);
    }
}
