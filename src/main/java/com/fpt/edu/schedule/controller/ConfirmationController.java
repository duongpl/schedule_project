package com.fpt.edu.schedule.controller;

import com.fpt.edu.schedule.model.Confirmation;
import com.fpt.edu.schedule.service.base.ConfirmationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/confirmations")
public class ConfirmationController {
    ConfirmationService confirmationService;
    @PostMapping
    public ResponseEntity addConfirm(@RequestParam("semesterId") int semesterId,
                                     @RequestBody List<Integer> lecturerIds) {

            return new ResponseEntity(confirmationService.save(lecturerIds,semesterId), HttpStatus.OK);
    }
    @PutMapping()
    public ResponseEntity updateStatusConfirm(@RequestBody Confirmation confirmation) {

            return new ResponseEntity(confirmationService.update(confirmation), HttpStatus.OK);

    }
    @GetMapping()
    public ResponseEntity getByLecturerAndSemester(@RequestParam("semesterId") int semesterId,
                                                   @RequestHeader("GoogleId") String lecturerId) {

            return new ResponseEntity(confirmationService.getByLecturerAndSemester(lecturerId,semesterId), HttpStatus.OK);

    }
}
