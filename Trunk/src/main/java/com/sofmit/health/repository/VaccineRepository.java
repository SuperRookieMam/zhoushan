package com.sofmit.health.repository;

import com.dm.common.repository.IdentifiableDtoRepository;
import com.sofmit.health.entity.Vaccine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;
import java.util.Optional;

public interface VaccineRepository extends JpaRepository<Vaccine, Long>, QuerydslPredicateExecutor<Vaccine>,
        IdentifiableDtoRepository<Vaccine, Long> {

    public Optional<Vaccine> findTopByOrderByIdDesc();

    public List<?> findEByName();

}
