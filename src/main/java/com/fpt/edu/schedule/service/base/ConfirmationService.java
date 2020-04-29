package com.fpt.edu.schedule.service.base;

import com.fpt.edu.schedule.model.Confirmation;

import java.util.List;

public interface ConfirmationService {
    List<Confirmation> save(List<Integer> lecturerIds,int semesterId);

    Confirmation update(Confirmation confirmation);

}
