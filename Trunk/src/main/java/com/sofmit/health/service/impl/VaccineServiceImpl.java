package com.sofmit.health.service.impl;

import com.dm.common.dto.RangePage;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sofmit.health.converter.VaccineConverter;
import com.sofmit.health.dto.VaccineDto;
import com.sofmit.health.entity.QVaccine;
import com.sofmit.health.entity.Vaccine;
import com.sofmit.health.repository.VaccineRepository;
import com.sofmit.health.service.VaccineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Optional;

@Service
public class VaccineServiceImpl implements VaccineService {

    @Autowired
    private VaccineRepository vaccineRepository;

    @Autowired
    private VaccineConverter vaccineConverter;

    private final QVaccine qVaccine = QVaccine.vaccine;
    @Autowired
    private JPAQueryFactory jpaQueryFactory;

    @Override
    @Transactional
    public Vaccine save(VaccineDto dto) {
        Vaccine model = new Vaccine();
        vaccineConverter.copyProperties(model, dto);
        return vaccineRepository.save(model);
    }

    @Override
    @Transactional
    public Vaccine update(Long id, VaccineDto dto) {
        Vaccine model = vaccineRepository.getOne(id);
        vaccineConverter.copyProperties(model, dto);
        return vaccineRepository.save(model);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        vaccineRepository.deleteById(id);
    }

    @Override
    public Optional<Vaccine> findById(Long id) {
        return vaccineRepository.findById(id);
    }

    @Override
    public RangePage<Vaccine> list(Long maxId, Pageable pageable) {
        BooleanBuilder query = new BooleanBuilder();
        maxId = Optional.ofNullable(maxId)
                .orElseGet(() -> vaccineRepository.findTopByOrderByIdDesc().map(Vaccine::getId).orElse(Long.MIN_VALUE));
        query.and(qVaccine.id.loe(maxId));
        Page<Vaccine> result = vaccineRepository.findAll(query, pageable);
        return RangePage.of(maxId, result);
    }

    @Override
    public RangePage<Vaccine> list(Map<String, String> map, Pageable pageable) {
        return findByMapPage(map, pageable, qVaccine, jpaQueryFactory);
    }

    @Override
    public Iterable<Vaccine> list(Map<String, String> map) {
        return findByMap(map, qVaccine, vaccineRepository);
    }
}
