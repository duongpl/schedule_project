package com.fpt.edu.schedule.controller;

import com.fpt.edu.schedule.model.ClassName;
import com.fpt.edu.schedule.model.Slot;
import com.fpt.edu.schedule.repository.base.QueryParam;
import com.fpt.edu.schedule.service.base.SlotService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/slots")
public class SlotController {
    SlotService slotService;
    @PostMapping("/filter")
    public ResponseEntity<ClassName> getSlotByCriteria(@RequestBody QueryParam queryParam) {

            List<Slot> slotList =slotService.findByCriteria(queryParam);
            return new ResponseEntity(slotList, HttpStatus.OK);

    }
}
