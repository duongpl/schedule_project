package com.fpt.edu.schedule.controller;

import com.fpt.edu.schedule.model.UserName;
import com.fpt.edu.schedule.service.base.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {
    UserService userService;
    @GetMapping
    public List<UserName> getAllUser(){
            return userService.getAllUser();
    }
    @GetMapping("/{userId}")
    public UserName getUserById(@PathVariable("userId") String userId){
        return userService.getUserNameById(userId);
    }

}
