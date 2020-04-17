package com.fpt.edu.schedule.service.impl;

import com.fpt.edu.schedule.model.*;
import com.fpt.edu.schedule.repository.base.*;
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
    TimetableRepository timetableRepository;
    SemesterService semesterService;
    LecturerRepository lecturerRepository;
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
        Timetable timetable = timetableRepository.findBySemester(semesterService.findById(semester));
        Lecturer lecturer = lecturerRepository.findByGoogleId(lecturerId);
        Set<ClassName> classes = timetable.getTimetableDetails().stream().filter(i -> i.getSubject().getDepartment().equals(lecturer.getDepartment()))
                .map(TimetableDetail::getClassName).collect(Collectors.toSet());
        return classes.stream().collect(Collectors.toList());
    }


}
