package com.fpt.edu.schedule.repository.base;


import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Repository;

@NoRepositoryBean
public interface BaseRepository<T,V> extends JpaSpecificationExecutor<T>, CrudRepository<T,V> {

}
