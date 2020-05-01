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
    TimetableRepository timetableRepo;
    SemesterRepository semesterRepo;
    LecturerService lecturerService;
    ConfirmationRepository confirmationRe;

    @Override
    public List<Confirmation> save(List<Integer> lecturerIds, int semesterId) {
        Semester semester = semesterRepo.findById(semesterId);
        lecturerIds.stream().forEach(i->{
            Lecturer lecture = lecturerService.findById(i);
            // find exist confirmation
            Confirmation con = confirmationRe.findBySemesterAndLecturer(semester,lecture);
            if(con == null) {
                confirmationRe.save(new Confirmation(TimetableStatus.PUBLIC, lecturerService.findById(i), semesterRepo.findById(semesterId)));
            } else {
                con.setStatus(TimetableStatus.PUBLIC);
                confirmationRe.save(con);
            }
        });
        return null;
    }

    @Override
    public Confirmation update(Confirmation confirmation) {
        Confirmation existed = confirmationRe.findById(confirmation.getId());
        if(confirmation.getStatus() != null) {
            existed.setStatus(confirmation.getStatus());
            existed.setReason(confirmation.getStatus().equals(TimetableStatus.REJECT) ? confirmation.getReason() : null);
        }
        existed.setConfirmed(true);
        return confirmationRe.save(existed);
    }

    @Override
    public Confirmation getByLecturerAndSemester(String lecturerId, int semesterId) {
        return confirmationRe
                .findBySemesterAndLecturer(semesterRepo.findById(semesterId),lecturerService.findByGoogleId(lecturerId));
    }


}
