package com.fpt.edu.schedule.controller;

import com.fpt.edu.schedule.common.enums.Status;
import com.fpt.edu.schedule.model.UserName;
import com.fpt.edu.schedule.repository.base.ClassNameRepository;
import com.fpt.edu.schedule.repository.base.QueryParam;
import com.fpt.edu.schedule.service.base.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;
@Transactional
@CrossOrigin
@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {
    UserService userService;
    ClassNameRepository classNameRepository;


    @GetMapping("/{userId}")
    public ResponseEntity<UserName> getUserById(@PathVariable("userId") String userId) {
        try {
            return new ResponseEntity(userService.getUserNameById(userId), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Test
    @PostMapping("/filter")
    public ResponseEntity<UserName> getUserByCriteria(@RequestBody QueryParam queryParam) {
        try {

            List<UserName> userNameList =userService.findByCriteria(queryParam);
            return new ResponseEntity(userNameList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping
    public ResponseEntity<UserName> updateUser(@RequestBody UserName userName) {
        try {
            return new ResponseEntity(userService.updateUserName(userName), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("/{userId}/updateStatus")
    public ResponseEntity<UserName> updateStatus(@RequestParam Status status,@PathVariable("userId") String userId) {
        try {
            return new ResponseEntity(userService.updateStatus(status,userId), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
