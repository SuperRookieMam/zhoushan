package com.sofmit.health.repository;

import com.sofmit.health.entity.Illness;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.Optional;

public interface IllnessRepository extends JpaRepository<Illness, Long>, QuerydslPredicateExecutor<Illness> {

    public Optional<Illness> findTopByOrderByIdDesc();

}
