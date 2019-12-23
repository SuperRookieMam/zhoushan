package com.sofmit.health.service;

import com.dm.common.dto.RangePage;
import com.sofmit.health.dto.VaccineDto;
import com.sofmit.health.entity.Vaccine;
import org.springframework.data.domain.Pageable;

import java.util.Map;
import java.util.Optional;

public interface VaccineService extends RefreshCopy {

    public Vaccine save(VaccineDto dto);

    public Vaccine update(Long id, VaccineDto dto);

    public void delete(Long id);

    public Optional<Vaccine> findById(Long id);

    public RangePage<Vaccine> list(Long maxId, Pageable pageable);

    RangePage<Vaccine> list(Map<String, String> map, Pageable pageable);

    Iterable<Vaccine> list(Map<String, String> map);
}
