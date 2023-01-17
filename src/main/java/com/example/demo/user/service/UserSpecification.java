package com.example.demo.user.service;

import com.example.demo.user.domain.User;
import com.example.demo.user.dto.UserSearch;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

public class UserSpecification implements Specification<User> {

    public UserSearch userSearch;

    public UserSpecification(final UserSearch userSearch) {
        this.userSearch = userSearch;
    }

    @Override
    public Predicate toPredicate(final Root<User> root, final CriteriaQuery<?> query, final CriteriaBuilder criteriaBuilder) {


        List<Predicate> predicates = new ArrayList<>();

        if (!ObjectUtils.isEmpty(userSearch.getFirstname())) {
            predicates.add(criteriaBuilder.like(root.get(User.Fields.firstname), "%"+userSearch.getFirstname().toLowerCase()+"%"));
        }
        if (!ObjectUtils.isEmpty(userSearch.getLastname())) {
            predicates.add(criteriaBuilder.like(root.get(User.Fields.lastname), "%"+userSearch.getLastname().toLowerCase()+"%"));
        }
        if (!ObjectUtils.isEmpty(userSearch.getLogin())) {
            predicates.add(criteriaBuilder.like(root.get(User.Fields.login), "%"+userSearch.getLogin().toLowerCase()+"%"));
        }
        if (!ObjectUtils.isEmpty(userSearch.getUserRole())) {
            predicates.add(criteriaBuilder.equal(root.get(User.Fields.userRole), userSearch.getUserRole()));
        }
        if (!ObjectUtils.isEmpty(userSearch.getSalaryMin())) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get(User.Fields.salaryPerHour), userSearch.getSalaryMin()));
        }
        if (!ObjectUtils.isEmpty(userSearch.getSalaryMax())) {
            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get(User.Fields.salaryPerHour), userSearch.getSalaryMax()));
        }

        return criteriaBuilder.and(predicates.toArray(Predicate[]::new));


    }
}