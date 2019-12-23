package com.sofmit.health.service;

import com.dm.common.dto.RangePage;
import com.sofmit.health.dto.PreventionDiseasesDto;
import com.sofmit.health.entity.PreventionDiseases;
import org.springframework.data.domain.Pageable;

import java.util.Map;
import java.util.Optional;

public interface PreventionDiseasesService extends RefreshCopy {

    public PreventionDiseases save(PreventionDiseasesDto dto);

    public PreventionDiseases update(Long id, PreventionDiseasesDto dto);

    public void delete(Long id);

    public Optional<PreventionDiseases> findById(Long id);

    public RangePage<PreventionDiseases> list(Long maxId, Pageable pageable);

    RangePage<PreventionDiseases> list(Map<String, String> map, Pageable pageable);

    Iterable<PreventionDiseases> list(Map<String, String> map);
}
