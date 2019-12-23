package com.sofmit.health.service.impl;

import com.dm.common.dto.RangePage;
import com.dm.file.repository.FileInfoRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sofmit.health.converter.CdcConverter;
import com.sofmit.health.dto.CdcDto;
import com.sofmit.health.entity.Cdc;
import com.sofmit.health.entity.QCdc;
import com.sofmit.health.repository.CdcRepository;
import com.sofmit.health.repository.VaccineRepository;
import com.sofmit.health.service.CdcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Optional;

@Service
public class CdcServiceImpl implements CdcService {

    @Autowired
    private CdcRepository cdcRepository;

    @Autowired
    private CdcConverter cdcConverter;

    @Autowired
    private FileInfoRepository fileInfoRepository;
    @Autowired
    private VaccineRepository vaccineRepository;

    private final QCdc qCdc = QCdc.cdc;

    @Autowired
    private JPAQueryFactory jpaQueryFactory;

    @Override
    @Transactional
    public Cdc save(CdcDto dto) {
        Cdc model = new Cdc();
        cdcConverter.copyProperties(model, dto);
        model.setLogo(fileInfoRepository.getByDto(dto.getLogo()));
        model.setImgs(fileInfoRepository.getByDto(dto.getImgs()));
        model.setVaccines(vaccineRepository.getByDto(dto.getVaccines()));
        return cdcRepository.save(model);
    }

    @Override
    @Transactional
    public Cdc update(Long id, CdcDto dto) {
        Cdc model = cdcRepository.getOne(id);
        cdcConverter.copyProperties(model, dto);
        model.setLogo(fileInfoRepository.getByDto(dto.getLogo()));
        model.setImgs(fileInfoRepository.getByDto(dto.getImgs()));
        model.setVaccines(vaccineRepository.getByDto(dto.getVaccines()));
        return cdcRepository.save(model);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        cdcRepository.deleteById(id);
    }

    @Override
    public Optional<Cdc> findById(Long id) {
        return cdcRepository.findById(id);
    }

    @Override
    public RangePage<Cdc> list(Long maxId, Pageable pageable) {
        BooleanBuilder query = new BooleanBuilder();
        maxId = Optional.ofNullable(maxId)
                .orElseGet(() -> cdcRepository.findTopByOrderByIdDesc().map(Cdc::getId).orElse(Long.MIN_VALUE));
        query.and(qCdc.id.loe(maxId));
        Page<Cdc> result = cdcRepository.findAll(query, pageable);
        return RangePage.of(maxId, result);
    }

    @Override
    public RangePage<Cdc> list(Map<String, String> map, Pageable pageable) {
        return findByMapPage(map, pageable, qCdc, jpaQueryFactory);
    }

    @Override
    public Iterable<Cdc> list(Map<String, String> map) {
        return findByMap(map, qCdc, cdcRepository);
    }
}
