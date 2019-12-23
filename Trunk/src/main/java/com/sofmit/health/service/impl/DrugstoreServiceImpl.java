package com.sofmit.health.service.impl;

import com.dm.common.dto.RangePage;
import com.dm.file.repository.FileInfoRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sofmit.health.converter.DrugstoreConverter;
import com.sofmit.health.dto.DrugstoreDto;
import com.sofmit.health.entity.Drugstore;
import com.sofmit.health.entity.QDrugstore;
import com.sofmit.health.repository.DrugstoreRepository;
import com.sofmit.health.service.DrugstoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.QSort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Optional;

@Service
public class DrugstoreServiceImpl implements DrugstoreService {

    @Autowired
    private DrugstoreRepository drugstoreRepository;

    @Autowired
    private DrugstoreConverter drugstoreConverter;

    private final QDrugstore qDrugstore = QDrugstore.drugstore;

    @Autowired
    private FileInfoRepository fileInfoReporitory;

    @Autowired
    private JPAQueryFactory jpaQueryFactory;

    @Override
    @Transactional
    public Drugstore save(DrugstoreDto dto) {
        Drugstore model = new Drugstore();
        drugstoreConverter.copyProperties(model, dto);
        model.setImgs(fileInfoReporitory.getByDto(dto.getImgs()));
        return drugstoreRepository.save(model);
    }

    @Override
    @Transactional
    public Drugstore update(Long id, DrugstoreDto dto) {
        Drugstore model = drugstoreRepository.getOne(id);
        drugstoreConverter.copyProperties(model, dto);
        model.setImgs(fileInfoReporitory.getByDto(dto.getImgs()));
        return drugstoreRepository.save(model);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        drugstoreRepository.deleteById(id);
    }

    @Override
    public Optional<Drugstore> findById(Long id) {
        return drugstoreRepository.findById(id);
    }

    @Override
    public RangePage<Drugstore> list(Long maxId, Pageable pageable) {
        BooleanBuilder query = new BooleanBuilder();
        maxId = Optional.ofNullable(maxId)
                .orElseGet(() -> drugstoreRepository.findTopByOrderByIdDesc().map(Drugstore::getId)
                        .orElse(Long.MIN_VALUE));
        query.and(qDrugstore.id.loe(maxId));
        pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(),
                QSort.by(qDrugstore.latitude.add(1).abs().asc()));
        Page<Drugstore> result = drugstoreRepository.findAll(query, pageable);
        return RangePage.of(maxId, result);
    }

    @Override
    public RangePage<Drugstore> list(Map<String, String> map, Pageable pageable) {
        return findByMapPage(map, pageable, qDrugstore, jpaQueryFactory);
    }

    @Override
    public Iterable<Drugstore> list(Map<String, String> map) {
        return findByMap(map, qDrugstore, drugstoreRepository);
    }

    @Override
    public Long count() {
        BooleanBuilder query = new BooleanBuilder();
        query.and(qDrugstore.state.eq("已发布"));
        return drugstoreRepository.count(query);
    }
}
