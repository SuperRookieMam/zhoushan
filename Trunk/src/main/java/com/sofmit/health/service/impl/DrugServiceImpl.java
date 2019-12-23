package com.sofmit.health.service.impl;

import com.dm.common.dto.RangePage;
import com.dm.file.repository.FileInfoRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sofmit.health.converter.DrugConverter;
import com.sofmit.health.dto.DrugDto;
import com.sofmit.health.entity.Drug;
import com.sofmit.health.entity.QDrug;
import com.sofmit.health.repository.DrugRepository;
import com.sofmit.health.service.DrugService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Optional;

@Service
public class DrugServiceImpl implements DrugService {

    @Autowired
    private DrugRepository drugRepository;

    @Autowired
    private DrugConverter drugConverter;

    private final QDrug qDrug = QDrug.drug;
    @Autowired
    private JPAQueryFactory jpaQueryFactory;
    @Autowired
    private FileInfoRepository fileInfoReporitory;

    @Override
    @Transactional
    public Drug save(DrugDto dto) {
        Drug model = new Drug();
        drugConverter.copyProperties(model, dto);
        model.setImgs(fileInfoReporitory.getByDto(dto.getImgs()));
        return drugRepository.save(model);
    }

    @Override
    @Transactional
    public Drug update(Long id, DrugDto dto) {
        Drug model = drugRepository.getOne(id);
        drugConverter.copyProperties(model, dto);
        model.setImgs(fileInfoReporitory.getByDto(dto.getImgs()));
        return drugRepository.save(model);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        drugRepository.deleteById(id);
    }

    @Override
    public Optional<Drug> findById(Long id) {
        return drugRepository.findById(id);
    }

    @Override
    public RangePage<Drug> list(Long maxId, Pageable pageable) {
        BooleanBuilder query = new BooleanBuilder();
        maxId = Optional.ofNullable(maxId)
                .orElseGet(() -> drugRepository.findTopByOrderByIdDesc().map(Drug::getId).orElse(Long.MIN_VALUE));
        query.and(qDrug.id.loe(maxId));
        Page<Drug> result = drugRepository.findAll(query, pageable);
        return RangePage.of(maxId, result);
    }

    @Override
    public RangePage<Drug> list(Map<String, String> map, Pageable pageable) {
        return findByMapPage(map, pageable, qDrug, jpaQueryFactory);
    }

    @Override
    public Iterable<Drug> list(Map<String, String> map) {
        return findByMap(map, qDrug, drugRepository);
    }

}
