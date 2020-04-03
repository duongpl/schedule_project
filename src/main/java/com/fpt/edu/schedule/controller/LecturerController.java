package com.fpt.edu.schedule.controller;

import com.fpt.edu.schedule.model.Lecturer;
import com.fpt.edu.schedule.repository.base.ClassNameRepository;
import com.fpt.edu.schedule.repository.base.QueryParam;
import com.fpt.edu.schedule.service.base.LecturerService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/lecturers")
public class LecturerController {
    LecturerService lecturerService;
    ClassNameRepository classNameRepository;


    @GetMapping("/{lecturerId}")
    public ResponseEntity<Lecturer> getLecturerById(@PathVariable("lecturerId") String lecturerId) {
        try {
            return new ResponseEntity(lecturerService.findByGoogleId(lecturerId), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping()
    public ResponseEntity<Lecturer> addNewLecture(@RequestBody Lecturer lecturer,@RequestHeader("GoogleId") String hodGoogleId) {
        try {
            return new ResponseEntity(lecturerService.addLecture(lecturer,hodGoogleId), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @DeleteMapping("/{lecturerId}")
    public ResponseEntity<Lecturer> removeLecture(@PathVariable("lecturerId") int lecturerId) {
        try {
            lecturerService.remove(lecturerId);
            return new ResponseEntity( HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Test
    @PostMapping("/filter")
    public ResponseEntity<Lecturer> getLecturerByCriteria(@RequestBody QueryParam queryParam) {
        try {

            List<Lecturer> lecturerList = lecturerService.findByCriteria(queryParam);
            return new ResponseEntity(lecturerList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping
    public ResponseEntity<Lecturer> updateLecturer(@RequestBody Lecturer lecturer) {
        try {
            return new ResponseEntity(lecturerService.updateLecturerName(lecturer), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("/transferRole")
    public ResponseEntity<Lecturer> transferRole(@RequestHeader("GoogleId") String hodGoogleId,
                                                 @RequestParam("lecturerGoogleId") String lecturerGoogleId) {
        try {
            return new ResponseEntity(lecturerService.transferRole(hodGoogleId,lecturerGoogleId), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
