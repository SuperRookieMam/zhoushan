package com.sofmit.health.service.impl;

import com.dm.common.dto.RangePage;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sofmit.health.converter.DepartmentConverter;
import com.sofmit.health.dto.DepartmentDto;
import com.sofmit.health.entity.Department;
import com.sofmit.health.entity.QDepartment;
import com.sofmit.health.repository.HDepartmentRepository;
import com.sofmit.health.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Optional;

@Service("hDepartmentServiceImpl")
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private HDepartmentRepository departmentRepository;

    @Autowired
    private DepartmentConverter departmentConverter;

    private final QDepartment qDepartment = QDepartment.department;
    @Autowired
    private JPAQueryFactory jpaQueryFactory;

    @Override
    @Transactional
    public Department save(DepartmentDto dto) {
        Department model = new Department();
        departmentConverter.copyProperties(model, dto);
        return departmentRepository.save(model);
    }

    @Override
    @Transactional
    public Department update(Long id, DepartmentDto dto) {
        Department model = departmentRepository.getOne(id);
        departmentConverter.copyProperties(model, dto);
        return departmentRepository.save(model);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        departmentRepository.deleteById(id);
    }

    @Override
    public Optional<Department> findById(Long id) {
        return departmentRepository.findById(id);
    }

    @Override
    public RangePage<Department> list(Long maxId, Pageable pageable) {

        BooleanBuilder query = new BooleanBuilder();
        maxId = Optional.ofNullable(maxId)
                .orElseGet(() -> departmentRepository.findTopByOrderByIdDesc().map(Department::getId)
                        .orElse(Long.MIN_VALUE));
        query.and(qDepartment.id.loe(maxId));
        Page<Department> result = departmentRepository.findAll(query, pageable);
        return RangePage.of(maxId, result);
    }

    @Override
    public RangePage<Department> list(Map<String, String> map, Pageable pageable) {
        return findByMapPage(map, pageable, qDepartment, jpaQueryFactory);
    }

    @Override
    public Iterable<Department> list(Map<String, String> map) {
        return findByMap(map, qDepartment, departmentRepository);
    }
}
