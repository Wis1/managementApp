package com.example.demo.timesheet.repository;

import com.example.demo.projectuser.domain.ProjectUsers;
import com.example.demo.timesheet.domain.Timesheet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TimesheetRepository extends JpaRepository<Timesheet, Long> {

    List<Timesheet> findByProjectUsersIn(List<ProjectUsers> projectUsers);

    @EntityGraph(attributePaths = "projectUsers")
    @Query("""
            SELECT (COUNT(t) > 0) FROM Timesheet t
            WHERE (t.endUserInProject > ?1 AND t.endUserInProject <= ?2) OR (t.startUserInProject >= ?1 AND t.startUserInProject < ?2)
              OR (t.startUserInProject < ?1 AND t.endUserInProject > ?2) AND t.projectUsers.user.uuid = ?3""")
    boolean existTimeRecordBetweenDateTime(LocalDateTime startTime, LocalDateTime endTime, UUID userUuid);

    Optional<Timesheet> findByUuid(UUID uuid);

    void deleteByUuid(UUID uuid);

    @EntityGraph(attributePaths = {"projectUsers"})
    Page<Timesheet> findAll(Specification<Timesheet> specification, Pageable pageable);
}
