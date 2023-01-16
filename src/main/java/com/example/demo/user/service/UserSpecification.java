package com.example.demo.user.service;

import com.example.demo.user.domain.User;
import com.example.demo.user.dto.UserSearch;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserSpecification {
    public Specification<User> getUsers(UserSearch userSearch) {
        return (root, query, criteriaBuilder) -> {

            List<Predicate> predicates = new ArrayList<>();

            if (userSearch.getFirstname() != null && !userSearch.getFirstname().isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("firstname"), userSearch.getFirstname()));
            }
            if (userSearch.getLastname() != null && !userSearch.getLastname().isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("lastname"), userSearch.getLastname()));
            }
            if (userSearch.getLogin() != null && !userSearch.getLogin().isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("login"), userSearch.getLogin()));
            }
            if (userSearch.getUserRole() != null) {
                predicates.add(criteriaBuilder.equal(root.get("userRole"), userSearch.getUserRole()));
            }
            if (userSearch.getSalaryMin() != null) {
                predicates.add(criteriaBuilder.greaterThan(root.get("salaryPerHour"), userSearch.getSalaryMin()));
            }
            if (userSearch.getSalaryMax() != null) {
                predicates.add(criteriaBuilder.lessThan(root.get("salaryPerHour"), userSearch.getSalaryMax()));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));

        };
    }
}
