package com.fpt.edu.schedule.service.impl;

import com.fpt.edu.schedule.model.ClassName;
import com.fpt.edu.schedule.repository.base.BaseRepository;
import com.fpt.edu.schedule.repository.base.ClassNameRepository;
import com.fpt.edu.schedule.repository.impl.BaseRepositoryImpl;
import com.fpt.edu.schedule.repository.impl.QueryParam;
import com.fpt.edu.schedule.service.base.ClassNameService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ClassNameServiceImpl extends BaseRepositoryImpl implements ClassNameService {
    ClassNameRepository classNameRepository;
    @Override
    public void addClassName(ClassName className) {
        classNameRepository.save(className);

    }
    @Override
    public ClassName getClassNameByName(String name) {

       return classNameRepository.findByName(name);
    }

    @Override
    public List<ClassName> findByCriteria(QueryParam queryParam) {
        return findAllByCriteria(queryParam);
    }
}
