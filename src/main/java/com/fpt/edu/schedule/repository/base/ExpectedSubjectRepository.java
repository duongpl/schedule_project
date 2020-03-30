package com.fpt.edu.schedule.repository.base;

import com.fpt.edu.schedule.model.Expected;
import com.fpt.edu.schedule.model.ExpectedSubject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpectedSubjectRepository extends JpaRepository<ExpectedSubject,Integer> {
    void removeAllByExpected(Expected expected);

    ExpectedSubject save(ExpectedSubject expectedSubject);

    ExpectedSubject findById(int id);

}
