package com.sofmit.health.repository;

import com.sofmit.health.entity.DataDict;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.Optional;

public interface DataDictRepository extends JpaRepository<DataDict, Long>, QuerydslPredicateExecutor<DataDict> {

    public Optional<DataDict> findTopByOrderByIdDesc();

}
