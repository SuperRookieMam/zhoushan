package com.sofmit.health.service;

import com.dm.common.dto.RangePage;
import com.sofmit.health.dto.DrugDto;
import com.sofmit.health.entity.Drug;
import org.springframework.data.domain.Pageable;

import java.util.Map;
import java.util.Optional;

public interface DrugService extends RefreshCopy {

    public Drug save(DrugDto dto);

    public Drug update(Long id, DrugDto dto);

    public void delete(Long id);

    public Optional<Drug> findById(Long id);

    public RangePage<Drug> list(Long maxId, Pageable pageable);

    RangePage<Drug> list(Map<String, String> map, Pageable pageable);

    Iterable<Drug> list(Map<String, String> map);
}
