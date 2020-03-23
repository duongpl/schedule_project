package com.fpt.edu.schedule.repository.base;

import com.fpt.edu.schedule.model.Expected;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.Repository;

public interface ExpectedRepository extends Repository<Expected,Integer>, JpaSpecificationExecutor<Expected> {

    Expected save (Expected expected);

    Expected findById(int id);

    Expected removeById(int id);


}
