package com.fpt.edu.schedule.repository.base;

import com.fpt.edu.schedule.model.Expected;
import com.fpt.edu.schedule.model.ExpectedNote;
import com.fpt.edu.schedule.model.UserName;
import org.springframework.data.repository.Repository;

public interface ExpectedRepository extends Repository<Expected,Integer> {
    Expected findByUserName(UserName userName);

    void save (Expected expected);
}
