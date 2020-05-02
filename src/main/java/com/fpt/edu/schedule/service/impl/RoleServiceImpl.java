package com.fpt.edu.schedule.service.impl;

import com.fpt.edu.schedule.model.Role;
import com.fpt.edu.schedule.repository.base.RoleRepository;
import com.fpt.edu.schedule.service.base.RoleService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@AllArgsConstructor
@Service
@Transactional
public class RoleServiceImpl implements RoleService {
    RoleRepository roleRepo;
    @Override
    public Role getRoleByName(String name) {
        return roleRepo.findByRoleName(name);
    }

    @Override
    public void addRole(Role role) {
        roleRepo.save(role);
    }
}
