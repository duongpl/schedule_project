package com.fpt.edu.schedule.controller;

import com.fpt.edu.schedule.model.Request;
import com.fpt.edu.schedule.repository.base.QueryParam;
import com.fpt.edu.schedule.service.base.RequestService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/requests")
public class RequestController {
    RequestService requestService;


    @PostMapping("/generate")
    public ResponseEntity generateFile(@RequestParam("file") MultipartFile multipartFile,@RequestParam(name = "semesterId") int semesterId) {
        try {
            requestService.generateExcelFile(multipartFile,semesterId);
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/filter")
    public ResponseEntity<Request> getRequestByCriteria(@RequestBody QueryParam queryParam) {
        try {
            return new ResponseEntity(requestService.findByCriteria(queryParam), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping
    public ResponseEntity<Request> addRequest(@RequestBody Request request,
                                             @RequestHeader("GoogleId") String currentLecturerId) {
        try {
            return new ResponseEntity(requestService.addRequest(request,currentLecturerId), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping
    public ResponseEntity<Request> updateRequest(@RequestBody Request request,
                                                 @RequestHeader("GoogleId")String lecturerId) {
        try {
            return new ResponseEntity(requestService.updateRequest(request,lecturerId), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @DeleteMapping("/{reportId}")
    public ResponseEntity remove(@PathVariable("reportId") int expectedId) {
        try {
            requestService.removeRequestById(expectedId);
            return new ResponseEntity( HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//    @GetMapping("/sendMail")
//    public String sendMail(@RequestParam("lecturerId") String lecturerId) {
//        SimpleMailMessage msg = new SimpleMailMessage();
//        try {
//
//            msg.setSubject("Logout");
//            msg.setText("Ban vua logout");
//
//        }catch (Exception e){
//            System.out.println(e.getMessage());
//        }
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        if (auth != null) {
//
//        }
//        return "redirect:/login";
//    }

}
