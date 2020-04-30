package com.fpt.edu.schedule.service.impl;

import com.fpt.edu.schedule.common.enums.Day;
import com.fpt.edu.schedule.common.exception.InvalidRequestException;
import com.fpt.edu.schedule.dto.TimetableDetailDTO;
import com.fpt.edu.schedule.dto.TimetableEdit;
import com.fpt.edu.schedule.dto.TimetableView;
import com.fpt.edu.schedule.model.*;
import com.fpt.edu.schedule.repository.base.*;
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

    private LecturerService lecturerService;
    private TimetableDetailRepository timetableDetailRepo;
    private RoomService roomService;
    private ConfirmationRepository confirmationRepo;
    private SemesterRepository semesterRepository;
    final static List<Integer> slotNumbers = Arrays.asList(1,2,3,4,5,6);

    @Override
    public List<TimetableView> getTimetableForView(QueryParam queryParam) {

        List<TimetableDetail> timetableDetails = findByCriteria(queryParam, 1);
        List<TimetableDetailDTO> timetableDetailDTOS = timetableDetails.stream().map(i -> new TimetableDetailDTO(i.getId(), i.getLecturer() != null ? i.getLecturer().getShortName() : null, i.getRoom() != null ? i.getRoom().getName() : null,
                i.getClassName().getName(), i.getSlot().getName(), i.getSubject().getCode(), i.getTimetableStatus(),i.getReason())).collect(Collectors.toList());
        List<TimetableDetailDTO> timetableDetailDTOSnew = new ArrayList<>();
        timetableDetailDTOS.stream().forEach(i -> {

            // convert
            switch (i.getSlot()) {
                case "M1":
                    timetableDetailDTOSnew.add(new TimetableDetailDTO(i.getId(), i.getLecturerShortName(), i.getRoom(), i.getClassName(), i.getSlot(), i.getSubjectCode(), 1, Day.MON));
                    timetableDetailDTOSnew.add(new TimetableDetailDTO(i.getId(), i.getLecturerShortName(), i.getRoom(), i.getClassName(), i.getSlot(), i.getSubjectCode(), 1, Day.WED));
                    timetableDetailDTOSnew.add(new TimetableDetailDTO(i.getId(), i.getLecturerShortName(), i.getRoom(), i.getClassName(), i.getSlot(), i.getSubjectCode(), 1, Day.FRI));
                    break;
                case "M2":
                    timetableDetailDTOSnew.add(new TimetableDetailDTO(i.getId(), i.getLecturerShortName(), i.getRoom(), i.getClassName(), i.getSlot(), i.getSubjectCode(), 2, Day.MON));
                    timetableDetailDTOSnew.add(new TimetableDetailDTO(i.getId(), i.getLecturerShortName(), i.getRoom(), i.getClassName(), i.getSlot(), i.getSubjectCode(), 2, Day.WED));
                    timetableDetailDTOSnew.add(new TimetableDetailDTO(i.getId(), i.getLecturerShortName(), i.getRoom(), i.getClassName(), i.getSlot(), i.getSubjectCode(), 2, Day.FRI));
                    break;
                case "M3":
                    timetableDetailDTOSnew.add(new TimetableDetailDTO(i.getId(), i.getLecturerShortName(), i.getRoom(), i.getClassName(), i.getSlot(), i.getSubjectCode(), 3, Day.MON));
                    timetableDetailDTOSnew.add(new TimetableDetailDTO(i.getId(), i.getLecturerShortName(), i.getRoom(), i.getClassName(), i.getSlot(), i.getSubjectCode(), 3, Day.WED));
                    timetableDetailDTOSnew.add(new TimetableDetailDTO(i.getId(), i.getLecturerShortName(), i.getRoom(), i.getClassName(), i.getSlot(), i.getSubjectCode(), 3, Day.FRI));
                    break;
                case "E1":
                    timetableDetailDTOSnew.add(new TimetableDetailDTO(i.getId(), i.getLecturerShortName(), i.getRoom(), i.getClassName(), i.getSlot(), i.getSubjectCode(), 4, Day.MON));
                    timetableDetailDTOSnew.add(new TimetableDetailDTO(i.getId(), i.getLecturerShortName(), i.getRoom(), i.getClassName(), i.getSlot(), i.getSubjectCode(), 4, Day.WED));
                    timetableDetailDTOSnew.add(new TimetableDetailDTO(i.getId(), i.getLecturerShortName(), i.getRoom(), i.getClassName(), i.getSlot(), i.getSubjectCode(), 4, Day.FRI));
                    break;
                case "E2":
                    timetableDetailDTOSnew.add(new TimetableDetailDTO(i.getId(), i.getLecturerShortName(), i.getRoom(), i.getClassName(), i.getSlot(), i.getSubjectCode(), 5, Day.MON));
                    timetableDetailDTOSnew.add(new TimetableDetailDTO(i.getId(), i.getLecturerShortName(), i.getRoom(), i.getClassName(), i.getSlot(), i.getSubjectCode(), 5, Day.WED));
                    timetableDetailDTOSnew.add(new TimetableDetailDTO(i.getId(), i.getLecturerShortName(), i.getRoom(), i.getClassName(), i.getSlot(), i.getSubjectCode(), 5, Day.FRI));
                    break;
                case "E3":
                    timetableDetailDTOSnew.add(new TimetableDetailDTO(i.getId(), i.getLecturerShortName(), i.getRoom(), i.getClassName(), i.getSlot(), i.getSubjectCode(), 6, Day.MON));
                    timetableDetailDTOSnew.add(new TimetableDetailDTO(i.getId(), i.getLecturerShortName(), i.getRoom(), i.getClassName(), i.getSlot(), i.getSubjectCode(), 6, Day.WED));
                    timetableDetailDTOSnew.add(new TimetableDetailDTO(i.getId(), i.getLecturerShortName(), i.getRoom(), i.getClassName(), i.getSlot(), i.getSubjectCode(), 6, Day.FRI));
                    break;
                case "M4":
                    timetableDetailDTOSnew.add(new TimetableDetailDTO(i.getId(), i.getLecturerShortName(), i.getRoom(), i.getClassName(), i.getSlot(), i.getSubjectCode(), 1, Day.THU));
                    timetableDetailDTOSnew.add(new TimetableDetailDTO(i.getId(), i.getLecturerShortName(), i.getRoom(), i.getClassName(), i.getSlot(), i.getSubjectCode(), 1, Day.TUE));
                    timetableDetailDTOSnew.add(new TimetableDetailDTO(i.getId(), i.getLecturerShortName(), i.getRoom(), i.getClassName(), i.getSlot(), i.getSubjectCode(), 2, Day.TUE));
                    break;
                case "M5":
                    timetableDetailDTOSnew.add(new TimetableDetailDTO(i.getId(), i.getLecturerShortName(), i.getRoom(), i.getClassName(), i.getSlot(), i.getSubjectCode(), 3, Day.TUE));
                    timetableDetailDTOSnew.add(new TimetableDetailDTO(i.getId(), i.getLecturerShortName(), i.getRoom(), i.getClassName(), i.getSlot(), i.getSubjectCode(), 2, Day.THU));
                    timetableDetailDTOSnew.add(new TimetableDetailDTO(i.getId(), i.getLecturerShortName(), i.getRoom(), i.getClassName(), i.getSlot(), i.getSubjectCode(), 3, Day.THU));
                    break;
                case "E4":
                    timetableDetailDTOSnew.add(new TimetableDetailDTO(i.getId(), i.getLecturerShortName(), i.getRoom(), i.getClassName(), i.getSlot(), i.getSubjectCode(), 4, Day.THU));
                    timetableDetailDTOSnew.add(new TimetableDetailDTO(i.getId(), i.getLecturerShortName(), i.getRoom(), i.getClassName(), i.getSlot(), i.getSubjectCode(), 4, Day.TUE));
                    timetableDetailDTOSnew.add(new TimetableDetailDTO(i.getId(), i.getLecturerShortName(), i.getRoom(), i.getClassName(), i.getSlot(), i.getSubjectCode(), 5, Day.TUE));
                    break;
                case "E5":
                    timetableDetailDTOSnew.add(new TimetableDetailDTO(i.getId(), i.getLecturerShortName(), i.getRoom(), i.getClassName(), i.getSlot(), i.getSubjectCode(), 5, Day.THU));
                    timetableDetailDTOSnew.add(new TimetableDetailDTO(i.getId(), i.getLecturerShortName(), i.getRoom(), i.getClassName(), i.getSlot(), i.getSubjectCode(), 6, Day.THU));
                    timetableDetailDTOSnew.add(new TimetableDetailDTO(i.getId(), i.getLecturerShortName(), i.getRoom(), i.getClassName(), i.getSlot(), i.getSubjectCode(), 6, Day.TUE));
                    break;


            }
        });
        Map<Integer, List<TimetableDetailDTO>> collect = timetableDetailDTOSnew.stream().collect(Collectors.groupingBy(TimetableDetailDTO::getSlotNumber));
        List<TimetableView> timetableViews = collect
                .entrySet()
                .stream()
                .map(i -> new TimetableView(i.getKey(), i.getValue()))
                .collect(Collectors.toList());
        slotNumbers.stream().forEach(i->{
            if(!collect.keySet().contains(i)){
                timetableViews.add(new TimetableView(i,new ArrayList<>()));
            }
        });
        timetableViews
                .sort(Comparator.comparing(TimetableView::getSlotNumber));
        return timetableViews;
    }

    @Override
    public List<TimetableDetail> findByCriteria(QueryParam queryParam, int semesterId) {
        BaseSpecifications cns = new BaseSpecifications(queryParam);
        List<TimetableDetail> timetableDetails = timetableDetailRepo.findAll(cns);
        Semester semester = semesterRepository.findById(semesterId);
        timetableDetails.stream().forEach(i -> {
            Confirmation con = confirmationRepo.findBySemesterAndLecturer(semester, i.getLecturer());
            if (con != null) {
                i.setTimetableStatus(con.getStatus());
                i.setReason(con.getReason());
            }
        });
        return timetableDetailRepo.findAll(cns);
    }

    @Override
    public TimetableDetail updateTimetableDetail(TimetableDetailDTO timetableDetail) {
        TimetableDetail timetableDetailExisted = timetableDetailRepo.findById(timetableDetail.getId());
        Semester semester = timetableDetailExisted.getTimetable().getSemester();
        Confirmation con = confirmationRepo.findBySemesterAndLecturer(semester,timetableDetailExisted.getLecturer());
        Confirmation con1 = confirmationRepo.findBySemesterAndLecturer(semester,lecturerService.findByShortName(timetableDetail.getLecturerShortName()));
        if(con!=null){
            confirmationRepo.deleteById(con.getId());
        }
        if(con1!=null && !timetableDetail.getLecturerShortName().equalsIgnoreCase(timetableDetailExisted.getLecturer().getShortName())){
            confirmationRepo.deleteById(con1.getId());
        }
        if (timetableDetailExisted == null) {
            throw new InvalidRequestException("Not found this timetable !");
        }
        if (timetableDetail.getLecturerShortName() != null) {
            Lecturer lecturer = ("NOT_ASSIGN").equals(timetableDetail.getLecturerShortName()) ? null
                    : lecturerService.findByShortName(timetableDetail.getLecturerShortName());
            timetableDetailExisted.setLecturer(lecturer);

        }
        if (timetableDetail.getRoom() != null) {
            Room room = ("NOT_ASSIGN").equals(timetableDetail.getRoom()) ? null
                    : roomService.getRoomByName(timetableDetail.getRoom());
            timetableDetailExisted.setRoom(room);
        }
        return timetableDetailRepo.save(timetableDetailExisted);
    }

    @Override
    public List<TimetableEdit> getTimetableForEdit(QueryParam queryParam, String groupBy, int semesterId) {

        List<TimetableDetail> timetableDetails = findByCriteria(queryParam, semesterId);


        // check not_assign
        Object lecturer = queryParam.getInCriteria().get("lecturer");
        if (lecturer instanceof Map) {
            List<String> shortName = (List<String>) ((Map) lecturer).get("shortName");
            if (shortName.contains("NOT_ASSIGN")) {
                ArrayList arrayList = new ArrayList();
                arrayList.add(null);
                ((Map) lecturer).replace("shortName", arrayList);

                queryParam.getInCriteria().put("lecturer", lecturer);
                BaseSpecifications cns1 = new BaseSpecifications(queryParam);
                List<TimetableDetail> timetableDetails1 = timetableDetailRepo.findAll(cns1);
                if (shortName.size() != 1) {
                    timetableDetails.addAll(timetableDetails1);
                } else {
                    timetableDetails = timetableDetails1;
                }
            }
        }

        // check not_assign
        Object room = queryParam.getInCriteria().get("room");
        if (room instanceof Map) {
            List<String> roomName = (List<String>) ((Map) room).get("name");
            if (roomName.contains("NOT_ASSIGN")) {
                ArrayList arrayList = new ArrayList();
                arrayList.add(null);
                ((Map) room).replace("name", arrayList);
                queryParam.getInCriteria().put("room", room);
                BaseSpecifications cns1 = new BaseSpecifications(queryParam);
                List<TimetableDetail> timetableDetails1 = timetableDetailRepo.findAll(cns1);
                if (roomName.size() != 1) {
                    timetableDetails.addAll(timetableDetails1);
                } else {
                    timetableDetails = timetableDetails1;
                }
            }
        }
        Map<String, List<TimetableDetailDTO>> collect;
        List<TimetableDetailDTO> timetableDetailDTOS = timetableDetails.stream()
                .distinct()
                .map(i -> new TimetableDetailDTO(i.getId(),
                        i.getLecturer() != null ? i.getLecturer().getShortName() : " NOT_ASSIGN",
                        i.getRoom() != null ? i.getRoom().getName() : "NOT_ASSIGN",
                        i.getClassName().getName(), i.getSlot().getName(), i.getSubject().getCode(), i.getTimetableStatus(),i.getReason()))
                .collect(Collectors.toList());
        if (groupBy.equals("room")) {
            collect = timetableDetailDTOS.stream().collect(Collectors.groupingBy(TimetableDetailDTO::getRoom));
            List<TimetableEdit> timetableEdits = collect
                    .entrySet()
                    .stream()
                    .map(i -> new TimetableEdit(i.getKey(), i.getValue()))
                    .collect(Collectors.toList());
            timetableEdits
                    .sort(Comparator.comparing(TimetableEdit::getRoom));
            return timetableEdits;
        }
        collect = timetableDetailDTOS.stream().collect(Collectors.groupingBy(TimetableDetailDTO::getLecturerShortName));
        List<TimetableEdit> timetableEdits = collect
                .entrySet()
                .stream()
                .map(i -> new TimetableEdit(i.getKey(), i.getValue())).collect(Collectors.toList());
        timetableEdits
                .sort(Comparator.comparing(TimetableEdit::getRoom).reversed());
        return timetableEdits;
    }

    @Override
    public void swapTwoTimetableDetail(List<Integer> ids) {
        if (ids.size() != 2) {
            throw new InvalidRequestException("Don't select more than 2 items !");
        }
        TimetableDetail timetableDetail2 = timetableDetailRepo.findById(ids.get(1));
        TimetableDetail timetableDetail1 = timetableDetailRepo.findById(ids.get(0));
        TimetableDetailWrap t1 = new TimetableDetailWrap(timetableDetail1);
        TimetableDetailWrap t2 = new TimetableDetailWrap(timetableDetail2);
        swapLecturer(t1, t2);
        timetableDetailRepo.save(t1.detail);
        timetableDetailRepo.save(t2.detail);
    }

    public static void swapLecturer(TimetableDetailWrap cw1,
                                    TimetableDetailWrap cw2) {
        Lecturer temp = cw1.detail.getLecturer();
        cw1.detail.setLecturer(cw2.detail.getLecturer());
        cw2.detail.setLecturer(temp);
    }

    // wrap class for swap
    class TimetableDetailWrap {
        TimetableDetail detail;

        // Constructor
        TimetableDetailWrap(TimetableDetail detail) {
            this.detail = detail;
        }
    }

}
