package com.sofmit.health.service;

import com.dm.common.dto.RangePage;
import com.sofmit.health.dto.CdcDto;
import com.sofmit.health.entity.Cdc;
import org.springframework.data.domain.Pageable;

import java.util.Map;
import java.util.Optional;

public interface CdcService extends RefreshCopy {

    public Cdc save(CdcDto dto);

    public Cdc update(Long id, CdcDto dto);

    public void delete(Long id);

    public Optional<Cdc> findById(Long id);

    public RangePage<Cdc> list(Long maxId, Pageable pageable);

    RangePage<Cdc> list(Map<String, String> map, Pageable pageable);

    Iterable<Cdc> list(Map<String, String> map);
}
