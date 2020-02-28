package com.fpt.edu.schedule.repository.impl;

import com.fpt.edu.schedule.repository.base.BaseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Map;
@Repository
public class BaseRepositoryImpl<T,V>  {
    BaseRepository<T,V> baseRepository;
    public List<T> findAllByCriteria(QueryParam queryParam) {
        List<T> list = baseRepository.findAll(new Specification() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                System.out.println("abc");
                if (queryParam.getCriteria() != null) {
                    for (Map.Entry<String, Object> entry : queryParam.getCriteria().entrySet()) {
                        predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get(entry.getKey()), entry.getValue())));
                    }
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        });
        return list;
    }
}
