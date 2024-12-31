package com.ducnt.chillshaker.repository;

import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.hibernate.query.SortDirection;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@Service
public class GenericSpecification<T> {
    public Specification<T> getFilters(String q,
                                       String includeProperties,
                                       String attribute) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            assert query != null;

            if(q != null && !q.isBlank() && attribute != null && !attribute.isBlank()) {
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get(attribute)), "%" + q.toLowerCase() +"%")
                );
            }
            if(includeProperties != null && !includeProperties.isBlank()) {
                for (String includeProperty : includeProperties.split(",")) {
                    root.fetch(includeProperty, JoinType.LEFT);
                }
            }

            query.distinct(true);
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
