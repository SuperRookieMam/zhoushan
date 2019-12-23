package com.sofmit.health.service;

import com.dm.common.dto.RangePage;
import com.sofmit.health.dto.HospitalDto;
import com.sofmit.health.entity.Hospital;
import org.springframework.data.domain.Pageable;

import java.util.Map;
import java.util.Optional;

public interface HospitalService extends RefreshCopy {

    public Hospital save(HospitalDto dto);

    public Hospital update(Long id, HospitalDto dto);

    public void delete(Long id);

    public Optional<Hospital> findById(Long id);

    public RangePage<Hospital> list(Long maxId, Pageable pageable);

    RangePage<Hospital> list(Map<String, String> map, Pageable pageable);

    Iterable<Hospital> list(Map<String, String> map);

    Long count(String level);

    Map<String, Double> maxlatLog(Double lat, Double lng, Double rang);
}
