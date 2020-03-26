package com.fpt.edu.schedule.repository.base;

import com.fpt.edu.schedule.model.TimetableDetail;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface TimetableRepository extends Repository<TimetableDetail, Integer>, JpaSpecificationExecutor<TimetableDetail> {
    void save(TimetableDetail timetableDetail);

    List<TimetableDetail> findAll();

}
