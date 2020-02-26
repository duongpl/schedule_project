package com.fpt.edu.schedule.repository.base;

import com.fpt.edu.schedule.model.ClassName;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import java.util.List;
public interface ClassNameRepository extends Repository<ClassName,Integer> {
    void save(ClassName className);

    ClassName findByName(String name);

    //Test
    @Query(value = "SELECT * FROM class_name WHERE ?1 LIKE %?2%", nativeQuery = true)
    List<ClassName> findAllClassByCondition(String field,String value);
}
