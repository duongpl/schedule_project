package com.fpt.edu.schedule.repository.base;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter

public class QueryParam {


    private Map<String, Object> criteria;
    private String sortField;
    private boolean ascending = true;
    private Map<String, Object> inCriteria;

    public QueryParam() {

    }
}
