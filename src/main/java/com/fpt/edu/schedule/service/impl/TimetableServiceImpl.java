package com.fpt.edu.schedule.service.impl;

import com.fpt.edu.schedule.dto.TimetableDetailDTO;
import com.fpt.edu.schedule.dto.TimetableEdit;
import com.fpt.edu.schedule.model.Timetable;
import com.fpt.edu.schedule.model.TimetableDetail;
import com.fpt.edu.schedule.repository.base.SemesterRepository;
import com.fpt.edu.schedule.repository.base.TimetableRepository;
import com.fpt.edu.schedule.service.base.TimetableService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TimetableServiceImpl implements TimetableService {
    SemesterRepository semesterRepository;
    TimetableRepository timetableRepository;


    @Override
    public Timetable save(Timetable timeTable) {
        return null;
    }

    @Override
    public List<TimetableEdit> getTimetableBySemester(int semesterId) {
        List<TimetableDetail> timetableDetails = timetableRepository.findBySemester(semesterRepository.findById(semesterId)).getTimetableDetails();
        List<TimetableDetailDTO> timetableDetailDTOS = timetableDetails.stream().map(i -> new TimetableDetailDTO(i.getId(), i.getLecturer() != null ? i.getLecturer().getFullName() : null, i.getRoom().getName(),
                i.getClassName().getName(), i.getSlot().getName(), i.getSubject().getCode())).collect(Collectors.toList());
        List<TimetableEdit> timetableEdits = new ArrayList<>();
        Map<String, List<TimetableDetailDTO>> collect = timetableDetailDTOS.stream().collect(Collectors.groupingBy(TimetableDetailDTO::getSlot));
        collect.entrySet().stream().map(i-> new TimetableEdit(i.getKey(),i.getValue())).collect(Collectors.toList());

        return collect.entrySet().stream().map(i-> new TimetableEdit(i.getKey(),i.getValue())).collect(Collectors.toList());

    }
}
