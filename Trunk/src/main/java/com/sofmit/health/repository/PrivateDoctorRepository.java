package com.sofmit.health.repository;

import com.sofmit.health.entity.PrivateDoctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.Optional;

public interface PrivateDoctorRepository
        extends JpaRepository<PrivateDoctor, Long>, QuerydslPredicateExecutor<PrivateDoctor> {

    public Optional<PrivateDoctor> findTopByOrderByIdDesc();

}
