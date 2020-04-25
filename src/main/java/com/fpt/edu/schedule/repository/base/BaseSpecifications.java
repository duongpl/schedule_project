package com.fpt.edu.schedule.repository.base;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BaseSpecifications<T> implements Specification<T> {
    private QueryParam queryParam;

    public BaseSpecifications(QueryParam queryParam) {
        this.queryParam = queryParam;
    }

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        Map<String, Object> criteria = queryParam.getCriteria();
        Map<String, Object> inCriteria = queryParam.getInCriteria();
        ObjectMapper oMapper = new ObjectMapper();
        List<Predicate> predicates = new ArrayList();

        if (criteria != null) {
            for (Map.Entry<String, Object> entry : criteria.entrySet()) {
                if (entry.getValue() instanceof Map) {
                    Map<String, Object> mapEntry = oMapper.convertValue(entry.getValue(), Map.class);
                    for (Map.Entry<String, Object> entry1 : mapEntry.entrySet()) {
                        if (entry1.getValue() instanceof String) {
                            predicates.add(criteriaBuilder.like(getPath(root, entry.getKey()).get(entry1.getKey()), "%" + entry1.getValue() + "%"));
                        } else if (entry1.getValue() == null) {
                            predicates.add(criteriaBuilder.isNull(getPath(root, entry.getKey())));
                        } else {
                            predicates.add(criteriaBuilder.equal(getPath(root, entry.getKey()).get(entry1.getKey()), entry1.getValue()));
                        }

                    }
                } else {
                    if (entry.getValue() instanceof String) {
                        predicates.add(criteriaBuilder.like(root.get(entry.getKey()), "%" + entry.getValue() + "%"));
                    } else if (entry.getValue() == null) {
                        predicates.add(criteriaBuilder.isNull(root.get(entry.getKey())));
                    } else {
                        predicates.add(criteriaBuilder.equal(root.get(entry.getKey()), entry.getValue()));
                    }
                }
            }
        }
        if (inCriteria != null) {

            List<Predicate> predicateList1 = new ArrayList<>();
            Predicate predicate1 = null;
            for (Map.Entry<String, Object> entry : inCriteria.entrySet()) {
                if (entry.getValue() instanceof Map) {
                    Map<String, List<Object>> mapEntry = oMapper.convertValue(entry.getValue(), Map.class);
                    List<Predicate> predicateList = new ArrayList<>();
                    for (Map.Entry<String, List<Object>> entry1 : mapEntry.entrySet()) {
                        if (entry1.getValue().size() == 0) {
                            break;
                        }
                        entry1.getValue().forEach(i -> {
                            if (i instanceof String) {
                                predicateList.add(criteriaBuilder.like(getPath(root, entry.getKey()).get(entry1.getKey()), "%" + i + "%"));
                            } else if (i == null) {
                                predicateList.add(criteriaBuilder.isNull(getPath(root, entry.getKey())));
                            } else {
                                predicateList.add(criteriaBuilder.equal(getPath(root, entry.getKey()).get(entry1.getKey()), i));
                            }
                        });
                        predicate1 = criteriaBuilder.or(predicateList.toArray(new Predicate[0]));
                        predicateList1.add(predicate1);
                    }
                } else {
                    ArrayList<Object> array = (ArrayList) entry.getValue();
                    if (array.size() == 0) {
                        break;
                    }
                    array.forEach(i -> {

                        Predicate predicate = (i instanceof String) ? criteriaBuilder.like(root.get(entry.getKey()), "%" + i + "%")
                                : criteriaBuilder.equal(root.get(entry.getKey()), i);
//                        predicateList.add(predicate);
                    });
                }
            }

            predicates.add(criteriaBuilder.and(predicateList1.toArray(new Predicate[0])));
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

    protected Path<Comparable> getPath(Root<T> root, String key) {
        Path<Comparable> path;
        if (key.contains(".")) {
            String[] split = key.split("\\.");
            int keyPosition = 0;
            path = root.get(split[keyPosition]);
            for (String criteriaKeys : split) {
                if (keyPosition > 0) {
                    path = path.get(criteriaKeys);
                }
                keyPosition++;
            }
        } else {
            path = root.get(key);
        }
        return path;
    }


}
