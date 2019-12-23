package com.sofmit.health.service.impl;

import com.dm.common.dto.RangePage;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sofmit.health.converter.DataDictConverter;
import com.sofmit.health.dto.DataDictDto;
import com.sofmit.health.entity.DataDict;
import com.sofmit.health.entity.QDataDict;
import com.sofmit.health.repository.DataDictRepository;
import com.sofmit.health.service.DataDictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Optional;

@Service
public class DataDictServiceImpl implements DataDictService {

    @Autowired
    private DataDictRepository dataDictRepository;

    @Autowired
    private DataDictConverter dataDictConverter;

    private final QDataDict qDataDict = QDataDict.dataDict;
    @Autowired
    private JPAQueryFactory jpaQueryFactory;

    @Override
    @Transactional
    public DataDict save(DataDictDto dto) {
        DataDict model = new DataDict();
        dataDictConverter.copyProperties(model, dto);
        return dataDictRepository.save(model);
    }

    @Override
    @Transactional
    public DataDict update(Long id, DataDictDto dto) {
        DataDict model = dataDictRepository.getOne(id);
        dataDictConverter.copyProperties(model, dto);
        return dataDictRepository.save(model);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        dataDictRepository.deleteById(id);
    }

    @Override
    public Optional<DataDict> findById(Long id) {
        return dataDictRepository.findById(id);
    }

    @Override
    public RangePage<DataDict> list(Long maxId, Pageable pageable) {
        BooleanBuilder query = new BooleanBuilder();
        // TODO 改成这样
        maxId = Optional.ofNullable(maxId)
                .orElseGet(
                        () -> dataDictRepository.findTopByOrderByIdDesc().map(DataDict::getId).orElse(Long.MIN_VALUE));
        query.and(qDataDict.id.loe(maxId));
        Page<DataDict> result = dataDictRepository.findAll(query, pageable);
        return RangePage.of(maxId, result);
    }

    @Override
    public RangePage<DataDict> list(Map<String, String> map, Pageable pageable) {
        return findByMapPage(map, pageable, qDataDict, jpaQueryFactory);
    }

    @Override
    public Iterable<DataDict> list(Map<String, String> map) {
        return findByMap(map, qDataDict, dataDictRepository);
    }
}
