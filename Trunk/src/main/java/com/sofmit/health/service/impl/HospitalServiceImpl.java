package com.sofmit.health.service.impl;

import com.dm.common.dto.RangePage;
import com.dm.file.repository.FileInfoRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sofmit.health.converter.DepartmentConverter;
import com.sofmit.health.converter.HospitalConverter;
import com.sofmit.health.dto.HospitalDto;
import com.sofmit.health.entity.Department;
import com.sofmit.health.entity.Hospital;
import com.sofmit.health.entity.QHospital;
import com.sofmit.health.repository.HDepartmentRepository;
import com.sofmit.health.repository.HospitalRepository;
import com.sofmit.health.service.HospitalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.Id;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class HospitalServiceImpl implements HospitalService {

    @Autowired
    private HospitalRepository hospitalRepository;
    @Autowired
    private HospitalConverter hospitalConverter;
    @Autowired
    private FileInfoRepository fileInfoRepository;
    @Autowired
    private HDepartmentRepository departmentRepository;
    @Autowired
    private DepartmentConverter departmentConverter;
    private final QHospital qHospital = QHospital.hospital;
    @Autowired
    private JPAQueryFactory jpaQueryFactory;

    @Override
    @Transactional
    public Hospital save(HospitalDto dto) {
        Hospital model = new Hospital();
        hospitalConverter.copyProperties(model, dto);
        model.setImgs(fileInfoRepository.getByDto(dto.getImgs()));
        model.setDepartments(
                iterableCopy(departmentConverter, departmentRepository, Department.class, dto.getDepartments()));
        model.setLogo(fileInfoRepository.getByDto(dto.getLogo()));
        return hospitalRepository.save(model);
    }

    @Override
    @Transactional
    public Hospital update(Long id, HospitalDto dto) {
        Hospital model = hospitalRepository.getOne(id);
        hospitalConverter.copyProperties(model, dto);
        model.setImgs(fileInfoRepository.getByDto(dto.getImgs()));
        model.setDepartments(
                iterableCopy(departmentConverter, departmentRepository, Department.class, dto.getDepartments()));
        model.setLogo(fileInfoRepository.getByDto(dto.getLogo()));
        return hospitalRepository.save(model);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        hospitalRepository.deleteById(id);
    }

    @Override
    public Optional<Hospital> findById(Long id) {
        return hospitalRepository.findById(id);
    }

    @Override
    public RangePage<Hospital> list(Long maxId, Pageable pageable) {
        BooleanBuilder query = new BooleanBuilder();
        maxId = Optional.ofNullable(maxId)
                .orElseGet(
                        () -> hospitalRepository.findTopByOrderByIdDesc().map(Hospital::getId).orElse(Long.MIN_VALUE));
        query.and(qHospital.id.loe(maxId));
        Page<Hospital> result = hospitalRepository.findAll(query, pageable);
        return RangePage.of(maxId, result);
    }

    @Override
    public RangePage<Hospital> list(Map<String, String> map, Pageable pageable) {
        return findByMapPage(map, pageable, qHospital, jpaQueryFactory);
    }

    @Override
    public Iterable<Hospital> list(Map<String, String> map) {
        return findByMap(map, qHospital, hospitalRepository);
    }

    @Override
    public Long count(String level) {
        BooleanBuilder query = new BooleanBuilder();
        query.and(qHospital.state.eq("已发布"));
        if (!StringUtils.isEmpty(level)) {
            BooleanExpression expression = qHospital.level.like(level);
            if (level.equalsIgnoreCase("二级甲等")) {
                expression = expression.or(qHospital.level.like("2"));
            }else if (level.equalsIgnoreCase("三级甲等")) {
                expression= expression.or(qHospital.level.like("3"));
            }
            query.and(expression);
        }
        return hospitalRepository.count(query);
    }

    /**
     * 纬度: lat
     */
    @Override
    public Map<String, Double> maxlatLog(Double lat, Double lon, Double raidus) {
        Double latitude = lat;
        Double longitude = lon;

        Double degree = (24901 * 1609) / 360.0;
        double raidusMile = raidus;

        Double dpmLat = 1 / degree;
        Double radiusLat = dpmLat * raidusMile;
        Double minLat = latitude - radiusLat;
        Double maxLat = latitude + radiusLat;

        Double mpdLng = degree * Math.cos(latitude * (Math.PI / 180));
        Double dpmLng = 1 / mpdLng;
        Double radiusLng = dpmLng * raidusMile;
        Double minLng = longitude - radiusLng;
        Double maxLng = longitude + radiusLng;
        Map<String, Double> map = new HashMap<>();
        map.put("minLat", minLat);
        map.put("minLng", minLng);
        map.put("maxLat", maxLat);
        map.put("maxLng ", maxLng);
        return map;

    }

}
