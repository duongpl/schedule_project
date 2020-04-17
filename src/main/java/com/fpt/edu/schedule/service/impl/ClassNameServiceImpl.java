package com.fpt.edu.schedule.service.impl;

import com.fpt.edu.schedule.model.*;
import com.fpt.edu.schedule.repository.base.BaseSpecifications;
import com.fpt.edu.schedule.repository.base.ClassNameRepository;
import com.fpt.edu.schedule.repository.base.QueryParam;
import com.fpt.edu.schedule.repository.base.TimetableRepository;
import com.fpt.edu.schedule.service.base.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class ClassNameServiceImpl  implements ClassNameService {
    ClassNameRepository classNameRepository;
    TimetableRepository timetableService;
    SemesterService semesterService;
    LecturerService lecturerService;
    @Override
    public void addClassName(ClassName className) {
        classNameRepository.save(className);

    }

    @Override
    public List<ClassName> findByCriteria(QueryParam queryParam,String semesterId,String lecturerId) {
        if (semesterId.length() == 0) {
            BaseSpecifications cns = new BaseSpecifications(queryParam);
            return classNameRepository.findAll(cns);
        }
        int semester = Integer.parseInt(semesterId);
        Timetable timetable = timetableService.findBySemester(semesterService.findById(semester));
        Lecturer lecturer = lecturerService.findByGoogleId(lecturerId);
        Set<ClassName> classes = timetable.getTimetableDetails().stream().filter(i -> i.getSubject().getDepartment().equals(lecturer.getDepartment()))
                .map(TimetableDetail::getClassName).collect(Collectors.toSet());
        return classes.stream().collect(Collectors.toList());
    }


}
