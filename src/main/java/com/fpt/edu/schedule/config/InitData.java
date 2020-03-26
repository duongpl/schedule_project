package com.fpt.edu.schedule.config;

import com.fpt.edu.schedule.model.Role;
import com.fpt.edu.schedule.model.Semester;
import com.fpt.edu.schedule.service.base.RoleService;
import com.fpt.edu.schedule.service.base.SemesterService;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class InitData implements ApplicationListener<ContextRefreshedEvent> {

    private RoleService roleService;
    private SemesterService semesterService;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        if (roleService.getRoleByName("ROLE_ADMIN") == null) {
            roleService.addRole(new Role("ROLE_ADMIN"));
        }

        if (roleService.getRoleByName("ROLE_USER") == null) {
            roleService.addRole(new Role("ROLE_USER"));
        }
        if(semesterService.countAllSemester() == 0){
            semesterService.save(new Semester("summer","2020"));
        }
    }
}
