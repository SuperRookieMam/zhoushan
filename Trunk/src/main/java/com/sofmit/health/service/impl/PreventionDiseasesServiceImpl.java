package com.sofmit.health.service.impl;

import com.dm.common.dto.RangePage;
import com.dm.file.repository.FileInfoRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sofmit.health.converter.PreventionDiseasesConverter;
import com.sofmit.health.dto.PreventionDiseasesDto;
import com.sofmit.health.entity.PreventionDiseases;
import com.sofmit.health.entity.QPreventionDiseases;
import com.sofmit.health.repository.PreventionDiseasesRepository;
import com.sofmit.health.service.PreventionDiseasesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Optional;

@Service
public class PreventionDiseasesServiceImpl implements PreventionDiseasesService {

    @Autowired
    private PreventionDiseasesRepository preventionDiseasesRepository;

    @Autowired
    private PreventionDiseasesConverter preventionDiseasesConverter;

    private final QPreventionDiseases qPreventionDiseases = QPreventionDiseases.preventionDiseases;
    @Autowired
    private FileInfoRepository fileInfoReporitory;
    @Autowired
    private JPAQueryFactory jpaQueryFactory;

    @Override
    @Transactional
    public PreventionDiseases save(PreventionDiseasesDto dto) {
        PreventionDiseases model = new PreventionDiseases();
        preventionDiseasesConverter.copyProperties(model, dto);
        model.setAccessory(fileInfoReporitory.getByDto(dto.getAccessory()));
        model.setCoverImg(fileInfoReporitory.getByDto(dto.getCoverImg()));
        return preventionDiseasesRepository.save(model);
    }

    @Override
    @Transactional
    public PreventionDiseases update(Long id, PreventionDiseasesDto dto) {
        PreventionDiseases model = preventionDiseasesRepository.getOne(id);
        preventionDiseasesConverter.copyProperties(model, dto);
        model.setAccessory(fileInfoReporitory.getByDto(dto.getAccessory()));
        model.setCoverImg(fileInfoReporitory.getByDto(dto.getCoverImg()));
        return preventionDiseasesRepository.save(model);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        preventionDiseasesRepository.deleteById(id);
    }

    @Override
    public Optional<PreventionDiseases> findById(Long id) {
        return preventionDiseasesRepository.findById(id);
    }

    @Override
    public RangePage<PreventionDiseases> list(Long maxId, Pageable pageable) {
        BooleanBuilder query = new BooleanBuilder();
        maxId = Optional.ofNullable(maxId)
                .orElseGet(() -> preventionDiseasesRepository.findTopByOrderByIdDesc().map(PreventionDiseases::getId)
                        .orElse(Long.MIN_VALUE));
        query.and(qPreventionDiseases.id.loe(maxId));
        Page<PreventionDiseases> result = preventionDiseasesRepository.findAll(query, pageable);
        return RangePage.of(maxId, result);
    }

    @Override
    public RangePage<PreventionDiseases> list(Map<String, String> map, Pageable pageable) {
        return findByMapPage(map, pageable, qPreventionDiseases, jpaQueryFactory);
    }

    @Override
    public Iterable<PreventionDiseases> list(Map<String, String> map) {
        return findByMap(map, qPreventionDiseases, preventionDiseasesRepository);
    }
}
