package com.sofmit.health.repository;

import com.sofmit.health.entity.Cdc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.Optional;

public interface CdcRepository extends JpaRepository<Cdc, Long>, QuerydslPredicateExecutor<Cdc> {

    public Optional<Cdc> findTopByOrderByIdDesc();

}
