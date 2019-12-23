package com.sofmit.health.service;

import com.dm.common.dto.RangePage;
import com.sofmit.health.dto.IllnessDto;
import com.sofmit.health.entity.Illness;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface IllnessService extends RefreshCopy {

    public Illness save(IllnessDto dto);

    public Illness update(Long id, IllnessDto dto);

    public void delete(Long id);

    public Optional<Illness> findById(Long id);

    public RangePage<Illness> list(Long maxId, Pageable pageable);

    RangePage<Illness> list(Map<String, String> map, Pageable pageable);

    Iterable<Illness> list(Map<String, String> map);

    List<Map<String, String>> map();

    RangePage<Illness> list(Long maxId, String keyword, Pageable pageable);

}
