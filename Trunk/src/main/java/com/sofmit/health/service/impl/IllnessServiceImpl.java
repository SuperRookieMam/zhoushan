package com.sofmit.health.service.impl;

import com.dm.common.dto.RangePage;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sofmit.health.converter.DepartmentConverter;
import com.sofmit.health.converter.IllnessConverter;
import com.sofmit.health.dto.IllnessDto;
import com.sofmit.health.entity.Department;
import com.sofmit.health.entity.Illness;
import com.sofmit.health.entity.QIllness;
import com.sofmit.health.repository.HDepartmentRepository;
import com.sofmit.health.repository.IllnessRepository;
import com.sofmit.health.service.IllnessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.*;

@Service
public class IllnessServiceImpl implements IllnessService {

    @Autowired
    private IllnessRepository illnessRepository;
    @Autowired
    private IllnessConverter illnessConverter;

    private final QIllness qIllness = QIllness.illness;
    @Autowired
    private DepartmentConverter departmentConverter;
    @Autowired
    private HDepartmentRepository departmentRepository;
    @Autowired
    private JPAQueryFactory jpaQueryFactory;

    @Override
    @Transactional
    public Illness save(IllnessDto dto) {
        Illness model = new Illness();
        illnessConverter.copyProperties(model, dto);
        model.setDepartments(
                iterableCopy(departmentConverter, departmentRepository, Department.class, dto.getDepartments()));
        return illnessRepository.save(model);
    }

    @Override
    @Transactional
    public Illness update(Long id, IllnessDto dto) {
        Illness model = illnessRepository.getOne(id);
        illnessConverter.copyProperties(model, dto);
        model.setDepartments(
                iterableCopy(departmentConverter, departmentRepository, Department.class, dto.getDepartments()));
        return illnessRepository.save(model);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        illnessRepository.deleteById(id);
    }

    @Override
    public Optional<Illness> findById(Long id) {
        return illnessRepository.findById(id);
    }

    @Override
    public RangePage<Illness> list(Long maxId, Pageable pageable) {
        BooleanBuilder query = new BooleanBuilder();
        maxId = Optional.ofNullable(maxId)
                .orElseGet(() -> illnessRepository.findTopByOrderByIdDesc().map(Illness::getId).orElse(Long.MIN_VALUE));
        query.and(qIllness.id.loe(maxId));
        Page<Illness> result = illnessRepository.findAll(query, pageable);
        return RangePage.of(maxId, result);
    }

    @Override
    public RangePage<Illness> list(Map<String, String> map, Pageable pageable) {
        return findByMapPage(map, pageable, qIllness, jpaQueryFactory);
    }

    @Override
    public Iterable<Illness> list(Map<String, String> map) {
        return findByMap(map, qIllness, illnessRepository);
    }

    @Override
    public List<Map<String, String>> map() {
        Iterable<Illness> illnesses = findByMap(new HashMap<>(), qIllness, illnessRepository);
        List<Map<String, String>> list = new ArrayList<>();
        illnesses.forEach(ele -> {
            Map<String, String> map = new HashMap<>();
            map.put("crowd", ele.getCrowd());
            map.put("ageGroup", ele.getAgeGroup());
            map.put("bodyPart", ele.getBodyPart());
            map.put("name", ele.getName());
            list.add(map);
        });
        return list;
    }

    @Override
    public RangePage<Illness> list(Long maxId, String keyword, Pageable pageable) {
        BooleanBuilder query = new BooleanBuilder();
        if (Objects.isNull(maxId)) {
            Optional<Illness> optionalMax = illnessRepository.findTopByOrderByIdDesc();
            maxId = optionalMax.map(Illness::getId).orElse(Long.MIN_VALUE);
        }
        query.and(qIllness.id.loe(maxId)).and(qIllness.state.eq("已发布"));
        if (!ObjectUtils.isEmpty(keyword)) {
            String[] keywords = keyword.split(",");
            for (int i = 0; i < keywords.length; i++) {
                query.and(qIllness.bodyPart.containsIgnoreCase(keywords[i])
                        .or(qIllness.ageGroup.containsIgnoreCase(keywords[i]))
                        .or(qIllness.name.containsIgnoreCase(keywords[i]))
                        .or(qIllness.crowd.containsIgnoreCase(keywords[i])));
            }
        }
        Page<Illness> result = illnessRepository.findAll(query, pageable);
        return RangePage.of(maxId, result);
    }
}
