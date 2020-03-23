package com.fpt.edu.schedule.repository.base;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public class QueryParam {


    private Map<String, Object> criteria;
    private String sortField;
    private boolean ascending = true;
    private Map<String, Object> inCriteria;
}
