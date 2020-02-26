package com.fpt.edu.schedule.controller;

import com.fpt.edu.schedule.model.ClassName;
import com.fpt.edu.schedule.model.UserName;
import com.fpt.edu.schedule.repository.base.ClassNameRepository;
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
    ClassNameRepository classNameRepository;

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

    //Test
    @GetMapping("/class")
    public ResponseEntity<ClassName> getUserById1() {
        try {
            List<ClassName> classNames = classNameRepository.findAllClassByCondition("name","IA1501");
            return new ResponseEntity(classNameRepository.findAllClassByCondition("name","IA1501"),HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
