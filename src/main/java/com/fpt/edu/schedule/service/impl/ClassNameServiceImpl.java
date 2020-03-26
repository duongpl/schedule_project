package com.fpt.edu.schedule.service.impl;

import com.fpt.edu.schedule.model.ClassName;
import com.fpt.edu.schedule.model.TimetableDetail;
import com.fpt.edu.schedule.repository.base.ClassNameRepository;
import com.fpt.edu.schedule.repository.base.QueryParam;
import com.fpt.edu.schedule.service.base.ClassNameService;
import com.fpt.edu.schedule.service.base.TimeTableDetailService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class ClassNameServiceImpl  implements ClassNameService {
    ClassNameRepository classNameRepository;
    TimeTableDetailService timeTableDetailService;
    @Override
    public void addClassName(ClassName className) {
        classNameRepository.save(className);

    }

    @Override
    public List<ClassName> getAllClass() {
        QueryParam queryParam = new QueryParam();
        queryParam.setCriteria(new HashMap<>());
        List<TimetableDetail> timetableDetails = timeTableDetailService.findByCriteria(queryParam);
        return null;
    }


}
