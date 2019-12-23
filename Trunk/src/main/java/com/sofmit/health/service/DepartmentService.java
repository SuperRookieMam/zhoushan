package com.sofmit.health.service;

import com.dm.common.dto.RangePage;
import com.sofmit.health.dto.DepartmentDto;
import com.sofmit.health.entity.Department;
import org.springframework.data.domain.Pageable;

import java.util.Map;
import java.util.Optional;

public interface DepartmentService extends RefreshCopy {

    public Department save(DepartmentDto dto);

    public Department update(Long id, DepartmentDto dto);

    public void delete(Long id);

    public Optional<Department> findById(Long id);

    public RangePage<Department> list(Long maxId, Pageable pageable);

    RangePage<Department> list(Map<String, String> map, Pageable pageable);

    Iterable<Department> list(Map<String, String> map);
}
