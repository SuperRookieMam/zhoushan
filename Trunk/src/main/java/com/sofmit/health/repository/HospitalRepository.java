package com.sofmit.health.repository;

import com.sofmit.health.entity.Hospital;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import java.util.Optional;

public interface HospitalRepository extends JpaRepository<Hospital, Long>, QuerydslPredicateExecutor<Hospital> {

    public Optional<Hospital> findTopByOrderByIdDesc();

}
