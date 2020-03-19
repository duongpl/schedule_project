package com.fpt.edu.schedule.repository.base;

import java.awt.print.Pageable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fpt.edu.schedule.model.Schedule;
import com.fpt.edu.schedule.model.Subject;
import com.fpt.edu.schedule.model.UserName;
import org.springframework.data.jpa.domain.Specification;

import com.fpt.edu.schedule.model.ClassName;
import com.fpt.edu.schedule.repository.impl.QueryParam;

public class BaseSpecifications<T> implements Specification<T> {
    private QueryParam queryParam;

    public BaseSpecifications(QueryParam queryParam) {
        this.queryParam = queryParam;
    }

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        Map<String, Object> criteria = queryParam.getCriteria();
        Map<String, List<Object>> inCriteria = queryParam.getInCriteria();
        ObjectMapper oMapper = new ObjectMapper();
        String sortField = queryParam.getSortField();
        List<Predicate> predicates = new ArrayList();

        if (criteria != null) {
            for (Map.Entry<String, Object> entry : criteria.entrySet()) {
                if (entry.getValue() instanceof Map) {
                    Map<String, Object> mapEntry = oMapper.convertValue(entry.getValue(), Map.class);
                    for (Map.Entry<String, Object> entry1 : mapEntry.entrySet()) {
                        if (entry.getValue() instanceof String) {
                            predicates.add(criteriaBuilder.like(root.get(configKey(entry.getKey())).get(entry.getKey()), "%" + entry.getValue() + "%"));
                        } else {
                            predicates.add(criteriaBuilder.equal(root.get(configKey(entry.getKey())).get(entry1.getKey()), entry1.getValue()));
                        }
                    }
                } else {
                    if (entry.getValue() instanceof String) {
                        predicates.add(criteriaBuilder.like(root.get(configKey(entry.getKey())).get(entry.getKey()), "%" + entry.getValue() + "%"));
                    } else {
                        predicates.add(criteriaBuilder.equal(root.get(entry.getKey()), entry.getValue()));
                    }
                }
            }
        }
        if (inCriteria != null) {

            List<Predicate> predicateList = new ArrayList<>();

            for (Map.Entry<String, List<Object>> entry : inCriteria.entrySet()) {

                entry.getValue().forEach(i -> {
                    Predicate predicate = (i instanceof String) ? criteriaBuilder.like(root.get(entry.getKey()), "%" + i + "%")
                            : criteriaBuilder.equal(root.get(entry.getKey()), i);
                    predicateList.add(predicate);
                });
            }

            predicates.add(criteriaBuilder.or(predicateList.toArray(new Predicate[0])));
        }
        if (sortField != null && sortField.length() > 0) {
            query.orderBy(queryParam.isAscending() ? criteriaBuilder.asc(root.get(sortField)) : criteriaBuilder.desc(root.get(sortField)));
        }
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }

    private String configKey(String key) {
        if (key.contains(".")) {
            String[] split = key.split("\\.");
            return split[split.length - 1];
        }
        return key;
    }


}
