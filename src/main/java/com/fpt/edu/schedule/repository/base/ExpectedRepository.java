package com.fpt.edu.schedule.repository.base;

import com.fpt.edu.schedule.model.Expected;
import com.fpt.edu.schedule.model.UserName;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.Repository;

public interface ExpectedRepository extends Repository<Expected,Integer>, JpaSpecificationExecutor<Expected> {
    Expected findByUserName(UserName userName);

    Expected save (Expected expected);

    Expected findById(int id);


}
