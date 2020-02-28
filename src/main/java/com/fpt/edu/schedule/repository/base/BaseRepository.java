package com.fpt.edu.schedule.repository.base;


import jdk.nashorn.internal.runtime.Specialization;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BaseRepository<T,V> extends JpaSpecificationExecutor<T>, CrudRepository<T,V> {

}
