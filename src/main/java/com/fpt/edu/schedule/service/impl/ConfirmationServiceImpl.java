package com.fpt.edu.schedule.service.impl;

import com.fpt.edu.schedule.common.enums.TimetableStatus;
import com.fpt.edu.schedule.model.Confirmation;
import com.fpt.edu.schedule.model.Lecturer;
import com.fpt.edu.schedule.model.Semester;
import com.fpt.edu.schedule.repository.base.ConfirmationRepository;
import com.fpt.edu.schedule.repository.base.SemesterRepository;
import com.fpt.edu.schedule.repository.base.TimetableRepository;
import com.fpt.edu.schedule.service.base.ConfirmationService;
import com.fpt.edu.schedule.service.base.LecturerService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class ConfirmationServiceImpl implements ConfirmationService {
    TimetableRepository timetableRepository;
    SemesterRepository semesterRepository;
    LecturerService lecturerService;
    ConfirmationRepository confirmationRepository;

    @Override
    public List<Confirmation> save(List<Integer> lecturerIds, int semesterId) {
        Semester semester = semesterRepository.findById(semesterId);
        lecturerIds.stream().forEach(i->{
            Lecturer lecture = lecturerService.findById(i);
            // find exist confirmation
            Confirmation con = confirmationRepository.findBySemesterAndLecturer(semester,lecture);
            if(con == null) {
                confirmationRepository.save(new Confirmation(TimetableStatus.PUBLIC, lecturerService.findById(i), semesterRepository.findById(semesterId)));
            } else {
                con.setStatus(TimetableStatus.PUBLIC);
                confirmationRepository.save(con);
            }
        });
        return null;
    }

    @Override
    public Confirmation update(Confirmation confirmation) {
        Confirmation existed = confirmationRepository.findById(confirmation.getId());
        if(confirmation.getStatus() != null) {
            existed.setStatus(confirmation.getStatus());
        }
        existed.setReason(confirmation.getReason());

        return confirmationRepository.save(existed);
    }

    @Override
    public Confirmation getByLecturerAndSemester(String lecturerId, int semesterId) {
        return confirmationRepository
                .findBySemesterAndLecturer(semesterRepository.findById(semesterId),lecturerService.findByGoogleId(lecturerId));
    }


}
