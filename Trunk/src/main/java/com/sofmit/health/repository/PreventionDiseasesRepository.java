package com.sofmit.health.repository;

import com.sofmit.health.entity.PreventionDiseases;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.Optional;

public interface PreventionDiseasesRepository
        extends JpaRepository<PreventionDiseases, Long>, QuerydslPredicateExecutor<PreventionDiseases> {

    public Optional<PreventionDiseases> findTopByOrderByIdDesc();

}
