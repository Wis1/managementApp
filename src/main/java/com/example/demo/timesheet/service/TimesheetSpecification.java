package com.example.demo.timesheet.service;

import com.example.demo.projectuser.domain.ProjectUsers;
import com.example.demo.timesheet.domain.Timesheet;
import com.example.demo.timesheet.dto.TimesheetSearch;
import com.example.demo.user.domain.User;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ObjectUtils;
import jakarta.persistence.criteria.Predicate;

import java.util.ArrayList;
import java.util.List;


public class TimesheetSpecification implements Specification<Timesheet> {

    public TimesheetSearch timesheetSearch;
    public Timesheet timesheet;

    public TimesheetSpecification(final TimesheetSearch timesheetSearch) {
        this.timesheetSearch= timesheetSearch;
    }

    @Override
    public jakarta.persistence.criteria.Predicate toPredicate(final Root<Timesheet> root, final CriteriaQuery<?> query, final CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();

        if (!ObjectUtils.isEmpty(timesheetSearch.getUserUuid())) {
            predicates.add(criteriaBuilder.equal(root.get(Timesheet.Fields.projectUsers).get(ProjectUsers.Fields.user).get(User.Fields.uuid), timesheetSearch.getUserUuid()));
        }

        if (!ObjectUtils.isEmpty(timesheetSearch.getUserFirstname())) {
            predicates.add(criteriaBuilder.like(root.get(Timesheet.Fields.projectUsers).get(ProjectUsers.Fields.user).get(User.Fields.firstname), "%"+ timesheetSearch.getUserFirstname().toLowerCase()+"%"));
        }

        if (!ObjectUtils.isEmpty(timesheetSearch.getUserLastname())) {
            predicates.add(criteriaBuilder.like(root.get(Timesheet.Fields.projectUsers).get(ProjectUsers.Fields.user).get(User.Fields.lastname), "%"+ timesheetSearch.getUserLastname().toLowerCase()+"%"));
        }

        if (!ObjectUtils.isEmpty(timesheetSearch.getTimeFrom())) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get(Timesheet.Fields.startUserInProject), timesheetSearch.getTimeFrom()));
        }




        return criteriaBuilder.and(predicates.toArray(Predicate[]::new));
    }
}
