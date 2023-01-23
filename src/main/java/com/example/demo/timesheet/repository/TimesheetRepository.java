package com.example.demo.timesheet.repository;

import com.example.demo.projectuser.domain.ProjectUsers;
import com.example.demo.timesheet.domain.Timesheet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TimesheetRepository extends JpaRepository<Timesheet, Long> {

    List<Timesheet> findByProjectUsers(ProjectUsers projectUsers);
}
