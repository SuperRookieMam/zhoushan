package com.sofmit.health.service.impl;

import com.dm.common.dto.RangePage;
import com.dm.file.repository.FileInfoRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.SubQueryExpression;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.sql.SQLQuery;
import com.querydsl.sql.SQLQueryFactory;
import com.sofmit.health.converter.CommunityHospitalConverter;
import com.sofmit.health.dto.CommunityHospitalDto;
import com.sofmit.health.entity.CommunityHospital;
import com.sofmit.health.entity.QCdc;
import com.sofmit.health.entity.QCommunityHospital;
import com.sofmit.health.repository.CommunityHospitalRepository;
import com.sofmit.health.repository.VaccineRepository;
import com.sofmit.health.service.CommunityHospitalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import javax.persistence.EntityManager;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CommunityHospitalServiceImpl implements CommunityHospitalService {

    @Autowired
    private CommunityHospitalRepository communityHospitalRepository;

    @Autowired
    private CommunityHospitalConverter communityHospitalConverter;

    private final QCommunityHospital qCommunityHospital = QCommunityHospital.communityHospital;

    private final QCdc qCdc = QCdc.cdc;

    @Autowired
    private FileInfoRepository fileInfoReporitory;

    @Autowired
    private VaccineRepository vaccineRepository;
    @Autowired
    private JPAQueryFactory jpaQueryFactory;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private SQLQueryFactory sqlQueryFactory;
    @Override
    @Transactional
    public CommunityHospital save(CommunityHospitalDto dto) {
        CommunityHospital model = new CommunityHospital();
        return communityHospitalRepository.save(copyProperties(model, dto));
    }

    @Override
    @Transactional
    public CommunityHospital update(Long id, CommunityHospitalDto dto) {
        CommunityHospital model = communityHospitalRepository.getOne(id);
        return communityHospitalRepository.save(copyProperties(model, dto));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        communityHospitalRepository.deleteById(id);
    }

    @Override
    public Optional<CommunityHospital> findById(Long id) {
        return communityHospitalRepository.findById(id);
    }

    @Override
    public RangePage<CommunityHospital> list(Long maxId, Pageable pageable) {
        BooleanBuilder query = new BooleanBuilder();
        maxId = Optional.ofNullable(maxId)
                .orElseGet(() -> communityHospitalRepository.findTopByOrderByIdDesc().map(CommunityHospital::getId)
                        .orElse(Long.MIN_VALUE));
        query.and(qCommunityHospital.id.loe(maxId));
        Page<CommunityHospital> result = communityHospitalRepository.findAll(query, pageable);
        return RangePage.of(maxId, result);
    }

    private CommunityHospital copyProperties(CommunityHospital model, CommunityHospitalDto dto) {
        model = communityHospitalConverter.copyProperties(model, dto);
        model.setLogo(fileInfoReporitory.getByDto(dto.getLogo()));
        model.setPics(fileInfoReporitory.getByDto(dto.getPics()));
        model.setVaccines(vaccineRepository.getByDto(dto.getVaccines()));
        return model;
    }

    @Override
    public RangePage<CommunityHospital> list(Map<String, String> map, Pageable pageable) {
        return findByMapPage(map, pageable, qCommunityHospital, jpaQueryFactory);
    }

    @Override
    public Iterable<CommunityHospital> list(Map<String, String> map) {
        return findByMap(map, qCommunityHospital, communityHospitalRepository);
    }

    @Override
    public Long count() {
        BooleanBuilder query = new BooleanBuilder();
        query.and(qCommunityHospital.state.eq("已发布"));
        return communityHospitalRepository.count(query);
    }

    @Override
    public Page<Map<String,Object>> searchCdcAndCommunityHospital (Map<String, String> map, Pageable pageable) {
       double lat = ObjectUtils.isEmpty(map.get(SLAT))? 0d:Double.parseDouble(map.get(SLAT));
       double lon = ObjectUtils.isEmpty(map.get(SLON))? 0d:Double.parseDouble(map.get(SLON));
       String vacciname = ObjectUtils.isEmpty(map.get("vaccinesName"))?"%%":"%"+map.get("vaccinesName")+"%";
       String  name = ObjectUtils.isEmpty(map.get("name"))?"%%":"%"+map.get("name")+"%";
       long page = Long.parseLong(map.get(PAGE));
       long size = Long.parseLong(map.get(SIZE));
       List<Map<String,Object>> list = communityHospitalRepository.find(vacciname,lat,lon,page*size,size,name);
       Long total = communityHospitalRepository.findcount(vacciname,name);
       return new PageImpl<>(list,pageable,total);
    }

    @Override
    public Page<Map<String,Object>> searchCdcAndCommunityHospitalForSql (Map<String, String> map, Pageable pageable){
        NumberExpression<Double> latitudePath = Expressions.numberPath(Double.class, "latitude_").as("latitude");
        NumberExpression<Double> longitudePath = Expressions.numberPath(Double.class, "longitude_").as("longitude");
        List<Expression> chSelections = new ArrayList<>();
        chSelections.add(Expressions.numberPath(Long.class,"id_").as("id"));
        chSelections.add(Expressions.stringPath("name_").as("name"));
        chSelections.add(Expressions.stringPath("tel_").as("tel"));
        chSelections.add(longitudePath);
        chSelections.add(latitudePath);
        chSelections.add(Expressions.stringPath("address_").as("address"));
        chSelections.add(Expressions.stringPath("service_time").as("serviceTime"));
        chSelections.add(Expressions.stringPath("vaccines_name_").as("vaccinesName"));
        chSelections.add(Expressions.stringPath("state_").as("state"));
        chSelections.add(Expressions.asString("communityHospital").as("type"));
        SubQueryExpression<Tuple> s1 = sqlQueryFactory.select(chSelections.toArray(new Expression[0])).from(Expressions.stringPath("zs_community_hospital_"));
        List<Expression> cdcSelections = new ArrayList<>();
        cdcSelections.add(Expressions.numberPath(Long.class,"id_").as("id"));
        cdcSelections.add(Expressions.stringPath("name_").as("name"));
        cdcSelections.add(Expressions.stringPath("tel_").as("tel"));
        cdcSelections.add(longitudePath);
        cdcSelections.add(latitudePath);
        cdcSelections.add(Expressions.stringPath("address_").as("address"));
        cdcSelections.add(Expressions.stringPath("working_time_").as("serviceTime"));
        cdcSelections.add(Expressions.stringPath("vaccines_name_").as("vaccinesName"));
        cdcSelections.add(Expressions.stringPath("state_").as("state"));
        cdcSelections.add(Expressions.asString("cdc").as("type"));
        SubQueryExpression<Tuple> s2 = sqlQueryFactory.select(cdcSelections.toArray(new Expression[0])).from(Expressions.stringPath("zs_cdc_"));
        Expression<Tuple> unionPath = new SQLQuery<>().union(s1, s2).as("t1");
        // where 1=1
        BooleanExpression booleanExpression = Expressions.TRUE;
        if (!ObjectUtils.isEmpty(map.get("name"))) {
            booleanExpression = booleanExpression.and(Expressions.stringPath("name").containsIgnoreCase(map.get("name")));
        }
        if (!ObjectUtils.isEmpty(map.get("vaccinesName"))) {
            booleanExpression = booleanExpression.and(Expressions.stringPath("vaccinesName").containsIgnoreCase(map.get("vaccinesName")));
        }
        long page = Long.parseLong(map.get(PAGE));
        long size = Long.parseLong(map.get(SIZE));
        List<Expression> t1 = new ArrayList<>();
        t1.add(Expressions.numberPath(Long.class,"id"));
        t1.add(Expressions.stringPath("name"));
        t1.add(Expressions.stringPath("tel"));
        NumberExpression<Double>  tlatitude = Expressions.numberPath(Double.class,"latitude");
        NumberExpression<Double>  tlongitude = Expressions.numberPath(Double.class,"longitude");
        t1.add(tlatitude);
        t1.add(tlongitude);
        t1.add(Expressions.stringPath("address"));
        t1.add(Expressions.stringPath("serviceTime"));
        t1.add(Expressions.stringPath("vaccinesName"));
        t1.add(Expressions.stringPath("state"));
        t1.add(Expressions.stringPath("type"));
        NumberExpression<Double> defaultLatitudePath = Expressions.asNumber(tlatitude.coalesce(0d)).subtract(30);
        NumberExpression<Double> defaultLongitudePath = Expressions.asNumber(tlongitude.coalesce(0d)).subtract(30);
        NumberExpression<Double> aPath = tlatitude.multiply(tlatitude).add(tlongitude.multiply(tlongitude));
        List<Tuple> list = sqlQueryFactory.select(t1.toArray(new Expression[0])).from(unionPath).where(booleanExpression).offset(page*size).limit(size).orderBy(aPath.asc()).fetch();
        Long total = sqlQueryFactory.select(t1.toArray(new Expression[0])).from(unionPath).where(booleanExpression).fetchCount();
        String[] keys ={"id","name","tel","latitude","longitude","address","serviceTime","vaccinesName","state","type"};
        return new PageImpl<>(list.stream().map(ele-> {
            Map<String,Object> element =new HashMap<>();
            Object[] objects = ele.toArray();
            for (int i = 0; i < objects.length; i++) {
                element.put(keys[i],objects[i]);
            }
            return element;
        }).collect(Collectors.toList()),pageable,total);
    }


}
