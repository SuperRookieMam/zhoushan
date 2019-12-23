package com.sofmit.health.service;

import com.dm.common.dto.RangePage;
import com.sofmit.health.dto.CommunityHospitalDto;
import com.sofmit.health.entity.CommunityHospital;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;
import java.util.Optional;

public interface CommunityHospitalService extends RefreshCopy {

    public CommunityHospital save(CommunityHospitalDto dto);

    public CommunityHospital update(Long id, CommunityHospitalDto dto);

    public void delete(Long id);

    public Optional<CommunityHospital> findById(Long id);

    public RangePage<CommunityHospital> list(Long maxId, Pageable pageable);

    RangePage<CommunityHospital> list(Map<String, String> map, Pageable pageable);

    Iterable<CommunityHospital> list(Map<String, String> map);

    Long count();

    Page<Map<String,Object>> searchCdcAndCommunityHospital(Map<String, String> map, Pageable pageable);

    Page<Map<String,Object>> searchCdcAndCommunityHospitalForSql(Map<String, String> map, Pageable pageable);
}
