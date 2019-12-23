package com.sofmit.health.service.impl;

import com.dm.common.dto.RangePage;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sofmit.health.converter.VaccineTypeConverter;
import com.sofmit.health.dto.VaccineTypeDto;
import com.sofmit.health.entity.QVaccineType;
import com.sofmit.health.entity.VaccineType;
import com.sofmit.health.repository.VaccineTypeRepository;
import com.sofmit.health.service.VaccineTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Optional;

@Service
public class VaccineTypeServiceImpl implements VaccineTypeService {

    @Autowired
    private VaccineTypeRepository vaccineTypeRepository;

    @Autowired
    private VaccineTypeConverter vaccineTypeConverter;
    @Autowired
    private JPAQueryFactory jpaQueryFactory;

    private final QVaccineType qVaccineType = QVaccineType.vaccineType;

    @Override
    @Transactional
    public VaccineType save(VaccineTypeDto dto) {
        VaccineType model = new VaccineType();
        vaccineTypeConverter.copyProperties(model, dto);
        model.setParent(oneCopy(vaccineTypeConverter, vaccineTypeRepository, VaccineType.class, dto.getParent()));
        return vaccineTypeRepository.save(model);
    }

    @Override
    @Transactional
    public VaccineType update(Long id, VaccineTypeDto dto) {
        VaccineType model = vaccineTypeRepository.getOne(id);
        vaccineTypeConverter.copyProperties(model, dto);
        model.setParent(oneCopy(vaccineTypeConverter, vaccineTypeRepository, VaccineType.class, dto.getParent()));
        return vaccineTypeRepository.save(model);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        vaccineTypeRepository.deleteById(id);
    }

    @Override
    public Optional<VaccineType> findById(Long id) {
        return vaccineTypeRepository.findById(id);
    }

    @Override
    public RangePage<VaccineType> list(Long maxId, Pageable pageable) {
        BooleanBuilder query = new BooleanBuilder();
        maxId = Optional.ofNullable(maxId)
                .orElseGet(() -> vaccineTypeRepository.findTopByOrderByIdDesc().map(VaccineType::getId)
                        .orElse(Long.MIN_VALUE));
        query.and(qVaccineType.id.loe(maxId));
        Page<VaccineType> result = vaccineTypeRepository.findAll(query, pageable);
        return RangePage.of(maxId, result);
    }

    @Override
    public RangePage<VaccineType> list(Map<String, String> map, Pageable pageable) {
        return findByMapPage(map, pageable, qVaccineType, jpaQueryFactory);
    }

    @Override
    public Iterable<VaccineType> list(Map<String, String> map) {
        return findByMap(map, qVaccineType, vaccineTypeRepository);
    }
}
