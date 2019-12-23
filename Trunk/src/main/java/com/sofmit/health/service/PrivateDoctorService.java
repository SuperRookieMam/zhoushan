package com.sofmit.health.service;

import com.dm.common.dto.RangePage;
import com.sofmit.health.dto.PrivateDoctorDto;
import com.sofmit.health.entity.PrivateDoctor;
import org.springframework.data.domain.Pageable;

import java.util.Map;
import java.util.Optional;

public interface PrivateDoctorService extends RefreshCopy {

    public PrivateDoctor save(PrivateDoctorDto dto);

    public PrivateDoctor update(Long id, PrivateDoctorDto dto);

    public void delete(Long id);

    public Optional<PrivateDoctor> findById(Long id);

    public RangePage<PrivateDoctor> list(Long maxId, Pageable pageable);

    RangePage<PrivateDoctor> list(Map<String, String> map, Pageable pageable);

    Iterable<PrivateDoctor> list(Map<String, String> map);
}
