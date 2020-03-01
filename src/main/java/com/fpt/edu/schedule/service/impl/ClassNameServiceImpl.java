package com.fpt.edu.schedule.service.impl;

import com.fpt.edu.schedule.model.ClassName;
import com.fpt.edu.schedule.repository.base.ClassNameRepository;
import com.fpt.edu.schedule.repository.base.BaseSpecifications;
import com.fpt.edu.schedule.repository.impl.QueryParam;
import com.fpt.edu.schedule.service.base.ClassNameService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ClassNameServiceImpl  implements ClassNameService {
    ClassNameRepository classNameRepository;
    @Override
    public void addClassName(ClassName className) {
        classNameRepository.save(className);

    }
    @Override
    public List<ClassName> findByCriteria(QueryParam queryParam) {
    	BaseSpecifications cns = new BaseSpecifications(queryParam);

    	return classNameRepository.findAll(cns);
    }
}
