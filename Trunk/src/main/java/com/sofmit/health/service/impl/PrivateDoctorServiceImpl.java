package com.sofmit.health.service.impl;

import com.dm.common.dto.RangePage;
import com.dm.file.repository.FileInfoRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sofmit.health.converter.DepartmentConverter;
import com.sofmit.health.converter.PrivateDoctorConverter;
import com.sofmit.health.dto.PrivateDoctorDto;
import com.sofmit.health.entity.Department;
import com.sofmit.health.entity.PrivateDoctor;
import com.sofmit.health.entity.QPrivateDoctor;
import com.sofmit.health.repository.HDepartmentRepository;
import com.sofmit.health.repository.PrivateDoctorRepository;
import com.sofmit.health.service.PrivateDoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Optional;

@Service
public class PrivateDoctorServiceImpl implements PrivateDoctorService {

    @Autowired
    private PrivateDoctorRepository privateDoctorRepository;

    @Autowired
    private PrivateDoctorConverter privateDoctorConverter;

    private final QPrivateDoctor qPrivateDoctor = QPrivateDoctor.privateDoctor;
    @Autowired
    private FileInfoRepository fileInfoReporitory;
    @Autowired
    private DepartmentConverter departmentConverter;
    @Autowired
    private HDepartmentRepository departmentRepository;
    @Autowired
    private JPAQueryFactory jpaQueryFactory;

    @Override
    @Transactional
    public PrivateDoctor save(PrivateDoctorDto dto) {
        PrivateDoctor model = new PrivateDoctor();
        privateDoctorConverter.copyProperties(model, dto);
        model.setImgs(fileInfoReporitory.getByDto(dto.getImgs()));
        model.setDepartment(oneCopy(departmentConverter, departmentRepository, Department.class, dto.getDepartment()));
        return privateDoctorRepository.save(model);
    }

    @Override
    @Transactional
    public PrivateDoctor update(Long id, PrivateDoctorDto dto) {
        PrivateDoctor model = privateDoctorRepository.getOne(id);
        privateDoctorConverter.copyProperties(model, dto);
        model.setImgs(fileInfoReporitory.getByDto(dto.getImgs()));
        model.setDepartment(oneCopy(departmentConverter, departmentRepository, Department.class, dto.getDepartment()));
        return privateDoctorRepository.save(model);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        privateDoctorRepository.deleteById(id);
    }

    @Override
    public Optional<PrivateDoctor> findById(Long id) {
        return privateDoctorRepository.findById(id);
    }

    @Override
    public RangePage<PrivateDoctor> list(Long maxId, Pageable pageable) {
        BooleanBuilder query = new BooleanBuilder();
        maxId = Optional.ofNullable(maxId)
                .orElseGet(() -> privateDoctorRepository.findTopByOrderByIdDesc().map(PrivateDoctor::getId)
                        .orElse(Long.MIN_VALUE));
        query.and(qPrivateDoctor.id.loe(maxId));
        Page<PrivateDoctor> result = privateDoctorRepository.findAll(query, pageable);
        return RangePage.of(maxId, result);
    }

    @Override
    public RangePage<PrivateDoctor> list(Map<String, String> map, Pageable pageable) {
        return findByMapPage(map, pageable, qPrivateDoctor, jpaQueryFactory);
    }

    @Override
    public Iterable<PrivateDoctor> list(Map<String, String> map) {
        return findByMap(map, qPrivateDoctor, privateDoctorRepository);
    }
}
