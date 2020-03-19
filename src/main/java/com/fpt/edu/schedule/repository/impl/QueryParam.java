package com.fpt.edu.schedule.repository.impl;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public class QueryParam {


    private Map<String, Object> criteria;
    private String sortField;
    private boolean ascending = true;
    private Map<String, List<Object>> inCriteria;
}
