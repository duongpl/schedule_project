package com.fpt.edu.schedule.controller;

import com.fpt.edu.schedule.model.ClassName;
import com.fpt.edu.schedule.model.UserName;
import com.fpt.edu.schedule.repository.base.BaseRepository;
import com.fpt.edu.schedule.repository.base.ClassNameRepository;
import com.fpt.edu.schedule.repository.impl.BaseRepositoryImpl;
import com.fpt.edu.schedule.repository.impl.QueryParam;
import com.fpt.edu.schedule.service.base.ClassNameService;
import com.fpt.edu.schedule.service.base.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
    ClassNameService classNameService;


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
            return new ResponseEntity(userService.getUserNameById(userId), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Test
    @PostMapping("/filter")
    public ResponseEntity<ClassName> getUserById1(@RequestBody QueryParam queryParam) {
        try {

            List<ClassName> classNameList =classNameService.findByCriteria(queryParam);
            return new ResponseEntity(classNameList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
