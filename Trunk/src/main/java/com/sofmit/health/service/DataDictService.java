package com.sofmit.health.service;

import com.dm.common.dto.RangePage;
import com.sofmit.health.dto.DataDictDto;
import com.sofmit.health.entity.DataDict;
import org.springframework.data.domain.Pageable;

import java.util.Map;
import java.util.Optional;

public interface DataDictService extends RefreshCopy {

    public DataDict save(DataDictDto dto);

    public DataDict update(Long id, DataDictDto dto);

    public void delete(Long id);

    public Optional<DataDict> findById(Long id);

    public RangePage<DataDict> list(Long maxId, Pageable pageable);

    RangePage<DataDict> list(Map<String, String> map, Pageable pageable);

    Iterable<DataDict> list(Map<String, String> map);
}
