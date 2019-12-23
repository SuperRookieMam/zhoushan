package com.sofmit.health.repository;

import com.sofmit.health.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import java.util.Optional;

public interface HDepartmentRepository extends JpaRepository<Department, Long>, QuerydslPredicateExecutor<Department> {

    public Optional<Department> findTopByOrderByIdDesc();

}
