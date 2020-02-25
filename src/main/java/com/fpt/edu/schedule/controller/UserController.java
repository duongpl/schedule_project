package com.fpt.edu.schedule.controller;

import com.fpt.edu.schedule.model.UserName;
import com.fpt.edu.schedule.service.base.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin
@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {
    UserService userService;

    @GetMapping
    public ResponseEntity<UserName> getAllUser() {
        try {
            return new ResponseEntity(userService.getAllUser(), HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserName> getUserById(@PathVariable("userId") String userId) {
        try {
            return new ResponseEntity(userService.getUserNameById(userId),HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
