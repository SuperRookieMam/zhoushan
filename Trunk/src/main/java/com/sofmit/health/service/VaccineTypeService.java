package com.sofmit.health.service;

import com.dm.common.dto.RangePage;
import com.sofmit.health.dto.VaccineTypeDto;
import com.sofmit.health.entity.VaccineType;
import org.springframework.data.domain.Pageable;

import java.util.Map;
import java.util.Optional;

public interface VaccineTypeService extends RefreshCopy {

    public VaccineType save(VaccineTypeDto dto);

    public VaccineType update(Long id, VaccineTypeDto dto);

    public void delete(Long id);

    public Optional<VaccineType> findById(Long id);

    public RangePage<VaccineType> list(Long maxId, Pageable pageable);

    RangePage<VaccineType> list(Map<String, String> map, Pageable pageable);

    Iterable<VaccineType> list(Map<String, String> map);
}
