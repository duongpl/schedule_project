package com.fpt.edu.schedule.service.impl;

import com.fpt.edu.schedule.common.enums.Day;
import com.fpt.edu.schedule.common.exception.InvalidRequestException;
import com.fpt.edu.schedule.dto.TimetableView;
import com.fpt.edu.schedule.dto.TimetableDetailDTO;
import com.fpt.edu.schedule.dto.TimetableEdit;
import com.fpt.edu.schedule.model.Lecturer;
import com.fpt.edu.schedule.model.Room;
import com.fpt.edu.schedule.model.TimetableDetail;
import com.fpt.edu.schedule.repository.base.BaseSpecifications;
import com.fpt.edu.schedule.repository.base.QueryParam;
import com.fpt.edu.schedule.repository.base.TimetableDetailRepository;
import com.fpt.edu.schedule.service.base.LecturerService;
import com.fpt.edu.schedule.service.base.RoomService;
import com.fpt.edu.schedule.service.base.TimeTableDetailService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional
public class TimeTableDetailServiceImpl implements TimeTableDetailService {
    LecturerService lecturerService;
    TimetableDetailRepository timetableDetailRepository;
    RoomService roomService;


    @Override
    public List<TimetableView> getTimetableForView(QueryParam queryParam) {
        BaseSpecifications cns = new BaseSpecifications(queryParam);
        List<TimetableDetail> timetableDetails = timetableDetailRepository.findAll(cns);
        List<TimetableDetailDTO> timetableDetailDTOS = timetableDetails.stream().map(i -> new TimetableDetailDTO(i.getId(), i.getLecturer() != null ? i.getLecturer().getShortName() : null, i.getRoom().getName(),
                i.getClassName().getName(), i.getSlot().getName(), i.getSubject().getCode())).collect(Collectors.toList());
        List<TimetableDetailDTO> timetableDetailDTOSnew = new ArrayList<>();
        timetableDetailDTOS.forEach(i -> {
            switch (i.getSlot()) {
                case "M1":
                    timetableDetailDTOSnew.add(new TimetableDetailDTO(i.getId(),i.getLecturerShortName(),i.getRoom(),i.getClassName(),i.getSlot(),i.getSubjectCode(),1,Day.MON));
                    timetableDetailDTOSnew.add(new TimetableDetailDTO(i.getId(),i.getLecturerShortName(),i.getRoom(),i.getClassName(),i.getSlot(),i.getSubjectCode(),1,Day.WED));
                    timetableDetailDTOSnew.add(new TimetableDetailDTO(i.getId(),i.getLecturerShortName(),i.getRoom(),i.getClassName(),i.getSlot(),i.getSubjectCode(),1,Day.FRI));
                    break;
                case "M2":
                    timetableDetailDTOSnew.add(new TimetableDetailDTO(i.getId(),i.getLecturerShortName(),i.getRoom(),i.getClassName(),i.getSlot(),i.getSubjectCode(),2,Day.MON));
                    timetableDetailDTOSnew.add(new TimetableDetailDTO(i.getId(),i.getLecturerShortName(),i.getRoom(),i.getClassName(),i.getSlot(),i.getSubjectCode(),2,Day.WED));
                    timetableDetailDTOSnew.add(new TimetableDetailDTO(i.getId(),i.getLecturerShortName(),i.getRoom(),i.getClassName(),i.getSlot(),i.getSubjectCode(),2,Day.FRI));
                    break;
                case "M3":
                    timetableDetailDTOSnew.add(new TimetableDetailDTO(i.getId(),i.getLecturerShortName(),i.getRoom(),i.getClassName(),i.getSlot(),i.getSubjectCode(),3,Day.MON));
                    timetableDetailDTOSnew.add(new TimetableDetailDTO(i.getId(),i.getLecturerShortName(),i.getRoom(),i.getClassName(),i.getSlot(),i.getSubjectCode(),3,Day.WED));
                    timetableDetailDTOSnew.add(new TimetableDetailDTO(i.getId(),i.getLecturerShortName(),i.getRoom(),i.getClassName(),i.getSlot(),i.getSubjectCode(),3,Day.FRI));
                    break;
                case "E1":
                    timetableDetailDTOSnew.add(new TimetableDetailDTO(i.getId(),i.getLecturerShortName(),i.getRoom(),i.getClassName(),i.getSlot(),i.getSubjectCode(),4,Day.MON));
                    timetableDetailDTOSnew.add(new TimetableDetailDTO(i.getId(),i.getLecturerShortName(),i.getRoom(),i.getClassName(),i.getSlot(),i.getSubjectCode(),4,Day.WED));
                    timetableDetailDTOSnew.add(new TimetableDetailDTO(i.getId(),i.getLecturerShortName(),i.getRoom(),i.getClassName(),i.getSlot(),i.getSubjectCode(),4,Day.FRI));
                    break;
                case "E2":
                    timetableDetailDTOSnew.add(new TimetableDetailDTO(i.getId(),i.getLecturerShortName(),i.getRoom(),i.getClassName(),i.getSlot(),i.getSubjectCode(),5,Day.MON));
                    timetableDetailDTOSnew.add(new TimetableDetailDTO(i.getId(),i.getLecturerShortName(),i.getRoom(),i.getClassName(),i.getSlot(),i.getSubjectCode(),5,Day.WED));
                    timetableDetailDTOSnew.add(new TimetableDetailDTO(i.getId(),i.getLecturerShortName(),i.getRoom(),i.getClassName(),i.getSlot(),i.getSubjectCode(),5,Day.FRI));
                    break;
                case "E3":
                    timetableDetailDTOSnew.add(new TimetableDetailDTO(i.getId(),i.getLecturerShortName(),i.getRoom(),i.getClassName(),i.getSlot(),i.getSubjectCode(),6,Day.MON));
                    timetableDetailDTOSnew.add(new TimetableDetailDTO(i.getId(),i.getLecturerShortName(),i.getRoom(),i.getClassName(),i.getSlot(),i.getSubjectCode(),6,Day.WED));
                    timetableDetailDTOSnew.add(new TimetableDetailDTO(i.getId(),i.getLecturerShortName(),i.getRoom(),i.getClassName(),i.getSlot(),i.getSubjectCode(),6,Day.FRI));
                    break;
                case "M4":
                    timetableDetailDTOSnew.add(new TimetableDetailDTO(i.getId(),i.getLecturerShortName(),i.getRoom(),i.getClassName(),i.getSlot(),i.getSubjectCode(),1,Day.THU));
                    timetableDetailDTOSnew.add(new TimetableDetailDTO(i.getId(),i.getLecturerShortName(),i.getRoom(),i.getClassName(),i.getSlot(),i.getSubjectCode(),1,Day.TUE));
                    timetableDetailDTOSnew.add(new TimetableDetailDTO(i.getId(),i.getLecturerShortName(),i.getRoom(),i.getClassName(),i.getSlot(),i.getSubjectCode(),2,Day.TUE));
                    break;
                case "M5":
                    timetableDetailDTOSnew.add(new TimetableDetailDTO(i.getId(),i.getLecturerShortName(),i.getRoom(),i.getClassName(),i.getSlot(),i.getSubjectCode(),3,Day.TUE));
                    timetableDetailDTOSnew.add(new TimetableDetailDTO(i.getId(),i.getLecturerShortName(),i.getRoom(),i.getClassName(),i.getSlot(),i.getSubjectCode(),2,Day.THU));
                    timetableDetailDTOSnew.add(new TimetableDetailDTO(i.getId(),i.getLecturerShortName(),i.getRoom(),i.getClassName(),i.getSlot(),i.getSubjectCode(),3,Day.THU));
                    break;
                case "E4":
                    timetableDetailDTOSnew.add(new TimetableDetailDTO(i.getId(),i.getLecturerShortName(),i.getRoom(),i.getClassName(),i.getSlot(),i.getSubjectCode(),4,Day.THU));
                    timetableDetailDTOSnew.add(new TimetableDetailDTO(i.getId(),i.getLecturerShortName(),i.getRoom(),i.getClassName(),i.getSlot(),i.getSubjectCode(),4,Day.TUE));
                    timetableDetailDTOSnew.add(new TimetableDetailDTO(i.getId(),i.getLecturerShortName(),i.getRoom(),i.getClassName(),i.getSlot(),i.getSubjectCode(),5,Day.TUE));
                    break;
                case "E5":
                    timetableDetailDTOSnew.add(new TimetableDetailDTO(i.getId(),i.getLecturerShortName(),i.getRoom(),i.getClassName(),i.getSlot(),i.getSubjectCode(),5,Day.THU));
                    timetableDetailDTOSnew.add(new TimetableDetailDTO(i.getId(),i.getLecturerShortName(),i.getRoom(),i.getClassName(),i.getSlot(),i.getSubjectCode(),6,Day.THU));
                    timetableDetailDTOSnew.add(new TimetableDetailDTO(i.getId(),i.getLecturerShortName(),i.getRoom(),i.getClassName(),i.getSlot(),i.getSubjectCode(),6,Day.TUE));
                    break;


            }
        });
        Map<Integer, List<TimetableDetailDTO>> collect = timetableDetailDTOSnew.stream().collect(Collectors.groupingBy(TimetableDetailDTO::getSlotNumber));
        List<TimetableView> timetableViews = collect.entrySet().stream().map(i -> new TimetableView(i.getKey(), i.getValue())).collect(Collectors.toList());
        timetableViews.sort(Comparator.comparing(TimetableView::getSlotNumber));
        return timetableViews;
    }

    @Override
    public List<TimetableDetail> findByCriteria(QueryParam queryParam) {
        BaseSpecifications cns = new BaseSpecifications(queryParam);
        return timetableDetailRepository.findAll(cns);
    }

    @Override
    public TimetableDetail updateTimetableDetail(TimetableDetailDTO timetableDetail) {
        TimetableDetail timetableDetailExisted = timetableDetailRepository.findById(timetableDetail.getId());
        if (timetableDetailExisted == null) {
            throw new InvalidRequestException("Not found this timetable !");
        }
        if (timetableDetail.getLecturerShortName() != null) {
            Lecturer lecturer = timetableDetail.getLecturerShortName().equals("NOT_ASSIGN") ? null
                    : lecturerService.findByShortName(timetableDetail.getLecturerShortName());
            timetableDetailExisted.setLecturer(lecturer);
        }
        if (timetableDetail.getRoom() != null) {
            Room room = timetableDetail.getRoom().equals("NOT_ASSIGN") ? null
                    : roomService.getRoomByName(getValidRoom(timetableDetail, timetableDetailExisted));
            timetableDetailExisted.setRoom(room);
        }
        return timetableDetailRepository.save(timetableDetailExisted);
    }

    @Override
    public List<TimetableEdit> getTimetableForEdit(QueryParam queryParam) {
        BaseSpecifications cns = new BaseSpecifications(queryParam);
        List<TimetableDetail> timetableDetails = timetableDetailRepository.findAll(cns);
        List<TimetableDetailDTO> timetableDetailDTOS = timetableDetails.stream().map(i -> new TimetableDetailDTO(i.getId(), i.getLecturer() != null ? i.getLecturer().getFullName() : null, i.getRoom().getName(),
                i.getClassName().getName(), i.getSlot().getName(), i.getSubject().getCode())).collect(Collectors.toList());
        Map<String, List<TimetableDetailDTO>> collect = timetableDetailDTOS.stream().collect(Collectors.groupingBy(TimetableDetailDTO::getRoom));
        List<TimetableEdit> timetableEdits = collect.entrySet().stream().map(i -> new TimetableEdit(i.getKey(), i.getValue())).collect(Collectors.toList());
        timetableEdits.sort(Comparator.comparing(TimetableEdit::getRoom));
        return timetableEdits;
    }

    private String getValidRoom(TimetableDetailDTO timetableDetail, TimetableDetail timetableDetailExisted) {
        QueryParam queryParam = new QueryParam();
        Map<String, Object> criteria = new HashMap<>();
        Map<String, Object> roomMap = new HashMap<>();
        roomMap.put("name", timetableDetail.getRoom());
        Map<String, Object> slotMap = new HashMap<>();
        slotMap.put("name", timetableDetailExisted.getSlot().getName());
        criteria.put("room", roomMap);
        criteria.put("slot", slotMap);
        queryParam.setCriteria(criteria);
        if (findByCriteria(queryParam).size() > 0) {
            throw new InvalidRequestException("Already have class in room !");
        }
        return timetableDetail.getRoom();
    }
}
