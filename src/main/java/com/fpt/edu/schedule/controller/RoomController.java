package com.fpt.edu.schedule.controller;

import com.fpt.edu.schedule.model.Room;
import com.fpt.edu.schedule.repository.base.QueryParam;
import com.fpt.edu.schedule.service.base.RoomService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/rooms")
public class RoomController {
    RoomService roomService;

    @PostMapping("/filter")
    public ResponseEntity<Room> getRoomByCriteria(@RequestBody QueryParam queryParam,
                                                       @RequestParam(value = "semesterId", defaultValue = "") String semesterId,
                                                       @RequestHeader("GoogleId") String lecturerId) {
            List<Room> roomList = roomService.findByCriteria(queryParam, semesterId,lecturerId);
            return new ResponseEntity(roomList, HttpStatus.OK);

    }
    @GetMapping("/forUpdate")
    public ResponseEntity<Room> getRoomForUpdate(@RequestParam("timetableDetailId") int timetableDetailId) {

            List<Room> roomList = roomService.getRoomForUpdate(timetableDetailId);
            return new ResponseEntity(roomList, HttpStatus.OK);

    }
}
