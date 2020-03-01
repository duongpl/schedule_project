package com.fpt.edu.schedule.repository.base;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

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
		List<Predicate> predicates = new ArrayList();
		for (Map.Entry<String, Object> entry : criteria.entrySet()) {
			predicates.add(criteriaBuilder.equal(root.get(entry.getKey()), entry.getValue()));
		}
		return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
	}
}
