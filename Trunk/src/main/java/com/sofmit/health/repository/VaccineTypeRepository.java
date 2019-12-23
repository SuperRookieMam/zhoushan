package com.sofmit.health.repository;

import com.sofmit.health.entity.VaccineType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import java.util.Optional;

public interface VaccineTypeRepository
        extends JpaRepository<VaccineType, Long>, QuerydslPredicateExecutor<VaccineType> {

    public Optional<VaccineType> findTopByOrderByIdDesc();

}
