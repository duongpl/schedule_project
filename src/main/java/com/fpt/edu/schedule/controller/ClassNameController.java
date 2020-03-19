package com.fpt.edu.schedule.controller;

import com.fpt.edu.schedule.model.ClassName;
import com.fpt.edu.schedule.repository.impl.QueryParam;
import com.fpt.edu.schedule.service.base.ClassNameService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/classes")
public class ClassNameController {
    ClassNameService classNameService;
    @PostMapping("/filter")
    public ResponseEntity<ClassName> getClassByCriteria(@RequestBody QueryParam queryParam) {
        try {

            List<ClassName> classNameList =classNameService.findByCriteria(queryParam);
            return new ResponseEntity(classNameList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
