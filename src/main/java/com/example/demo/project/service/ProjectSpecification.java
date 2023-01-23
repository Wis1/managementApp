package com.example.demo.project.service;

import com.example.demo.project.domain.Project;
import com.example.demo.project.dto.ProjectSearch;
import com.example.demo.user.domain.User;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

public class ProjectSpecification implements Specification<Project> {

    public ProjectSearch projectSearch;
    public Project project;

    public ProjectSpecification(final ProjectSearch projectSearch) {
        this.projectSearch= projectSearch;
    }

    @Override
    public Predicate toPredicate(final Root<Project> root, final CriteriaQuery<?> query, final CriteriaBuilder criteriaBuilder) {

        List<Predicate> predicates= new ArrayList<>();

        if (!ObjectUtils.isEmpty(projectSearch.getName())) {
            predicates.add(criteriaBuilder.like(root.get(Project.Fields.name),
                    "%"+projectSearch.getName().toLowerCase()+"%"));
        }
        if (!ObjectUtils.isEmpty(projectSearch.getStartProject())) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get(Project.Fields.startProject),
                    projectSearch.getStartProject()));
        }
        if (!ObjectUtils.isEmpty(projectSearch.getEndProject())) {
            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get(Project.Fields.endProject),
                    projectSearch.getEndProject()));
        }
        if (!ObjectUtils.isEmpty(projectSearch.getUserUuidList())) {
            projectSearch.getUserUuidList().forEach(uuid -> predicates.add(
                    criteriaBuilder.isTrue(
                            root.join(Project.Fields.userList).get(User.Fields.uuid)
                                    .in(uuid)
                    )
            ));
            predicates.add(criteriaBuilder.isTrue(root.get(Project.Fields.userList).get(Project.Fields.uuid)
                    .in(projectSearch.getUserUuidList())));
        }
        if(!ObjectUtils.isEmpty(projectSearch.getOverBudget())) {
            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get(Project.Fields.percentageBudgetUsed), 100));
        }

        return criteriaBuilder.and(predicates.toArray(Predicate[]::new));
    }
}
