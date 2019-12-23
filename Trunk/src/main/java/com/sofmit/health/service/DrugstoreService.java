package com.sofmit.health.service;

import com.dm.common.dto.RangePage;
import com.sofmit.health.dto.DrugstoreDto;
import com.sofmit.health.entity.Drugstore;
import org.springframework.data.domain.Pageable;

import java.util.Map;
import java.util.Optional;

public interface DrugstoreService extends RefreshCopy {

    public Drugstore save(DrugstoreDto dto);

    public Drugstore update(Long id, DrugstoreDto dto);

    public void delete(Long id);

    public Optional<Drugstore> findById(Long id);

    public RangePage<Drugstore> list(Long maxId, Pageable pageable);

    RangePage<Drugstore> list(Map<String, String> map, Pageable pageable);

    Iterable<Drugstore> list(Map<String, String> map);

    Long count();
}
