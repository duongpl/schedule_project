package com.fpt.edu.schedule.controller;

import com.fpt.edu.schedule.repository.base.ClassNameRepository;
import com.fpt.edu.schedule.service.base.ClassNameService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/classes")
public class ClassNameController {
    ClassNameService classNameService;
    ClassNameRepository classNameRepository;

}
