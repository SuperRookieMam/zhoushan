package com.sofmit.health.repository;

import com.sofmit.health.entity.Drug;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import java.util.Optional;

public interface DrugRepository extends JpaRepository<Drug, Long>, QuerydslPredicateExecutor<Drug> {

    public Optional<Drug> findTopByOrderByIdDesc();

}
