package com.sofmit.health.repository;

import com.sofmit.health.entity.Drugstore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.Optional;

public interface DrugstoreRepository extends JpaRepository<Drugstore, Long>, QuerydslPredicateExecutor<Drugstore> {

    public Optional<Drugstore> findTopByOrderByIdDesc();

}
