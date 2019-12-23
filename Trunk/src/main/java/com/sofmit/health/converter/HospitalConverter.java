package com.sofmit.health.converter;

import com.dm.common.converter.AbstractConverter;
import com.dm.file.converter.FileInfoConverter;
import com.sofmit.health.dto.DepartmentDto;
import com.sofmit.health.dto.HospitalDto;
import com.sofmit.health.entity.Hospital;
import org.apache.commons.collections4.IterableUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
public class HospitalConverter extends AbstractConverter<Hospital, HospitalDto> {

    @Autowired
    private FileInfoConverter fileInfoConverter;
    @Autowired
    private DepartmentConverter departmentConverter;

    @Override
    protected HospitalDto toDtoActual(Hospital model) {
        HospitalDto dto = new HospitalDto();
        dto.setId(model.getId());
        dto.setName(model.getName());
        dto.setLevel(model.getLevel());
        dto.setLongitude(model.getLongitude());
        dto.setLatitude(model.getLatitude());
        dto.setAddress(model.getAddress());
        dto.setMedicalInsuranceTel(model.getMedicalInsuranceTel());
        dto.setDescription(model.getDescription());
        dto.setLogo(fileInfoConverter.toDto(model.getLogo()).orElse(null));
        dto.setImgs(fileInfoConverter.toDto(model.getImgs()));
        // dto.setDepartments(departmentConverter.toDto(model.getDepartments()));
        if (!ObjectUtils.isEmpty(model.getDepartments()))
            dto.setDepartments(model.getDepartments().stream().map(ele -> {
                DepartmentDto departmentdto = new DepartmentDto();
                departmentdto.setId(ele.getId());
                departmentdto.setName(ele.getName());
                departmentdto.setPid(ele.getPid());
                departmentdto.setPname(ele.getPname());
                departmentdto.setState(ele.getState());
                departmentdto.setCreatedDate(ele.getCreatedDate());
                departmentdto.setLastModifiedDate(ele.getLastModifiedDate());
                return departmentdto;
            }).collect(Collectors.toList()));
        dto.setWorkingTime(model.getWorkingTime());
        dto.setCreatedDate(model.getCreatedDate());
        dto.setLastModifiedDate(model.getLastModifiedDate());
        dto.setState(model.getState());
        dto.setCreatedDate(model.getCreatedDate());
        dto.setLastModifiedDate(model.getLastModifiedDate());
        dto.setDepartmentsName(model.getDepartmentsName());
        dto.setInId(model.getInId());
        return dto;
    }

    public HospitalDto toList(Hospital model) {
        HospitalDto dto = new HospitalDto();
        dto.setId(model.getId());
        dto.setName(model.getName());
        dto.setLevel(model.getLevel());
        dto.setLongitude(model.getLongitude());
        dto.setLatitude(model.getLatitude());
        dto.setAddress(model.getAddress());
        dto.setMedicalInsuranceTel(model.getMedicalInsuranceTel());
        // dto.setDescription(model.getDescription());
        dto.setLogo(fileInfoConverter.toDto(model.getLogo()).orElse(null));
        dto.setImgs(fileInfoConverter.toDto(model.getImgs()));
        // dto.setDepartments(departmentConverter.toDto(model.getDepartments()));
        if (!ObjectUtils.isEmpty(model.getDepartments()))
            dto.setDepartments(model.getDepartments().stream().map(ele -> {
                DepartmentDto departmentdto = new DepartmentDto();
                departmentdto.setId(ele.getId());
                departmentdto.setName(ele.getName());
                departmentdto.setPid(ele.getPid());
                departmentdto.setPname(ele.getPname());
                departmentdto.setState(ele.getState());
                departmentdto.setCreatedDate(ele.getCreatedDate());
                departmentdto.setLastModifiedDate(ele.getLastModifiedDate());
                return departmentdto;
            }).collect(Collectors.toList()));
        dto.setWorkingTime(model.getWorkingTime());
        dto.setCreatedDate(model.getCreatedDate());
        dto.setLastModifiedDate(model.getLastModifiedDate());
        dto.setState(model.getState());
        dto.setCreatedDate(model.getCreatedDate());
        dto.setLastModifiedDate(model.getLastModifiedDate());
        dto.setDepartmentsName(model.getDepartmentsName());
        dto.setInId(model.getInId());
        return dto;
    }

    public List<HospitalDto> toList(Iterable<Hospital> models) {
        return IterableUtils.isEmpty(models) ? Collections.emptyList()
                : (List) StreamSupport.stream(models.spliterator(), false).map(this::toList)
                        .collect(Collectors.toList());
    }

    @Override
    public Hospital copyProperties(Hospital model, HospitalDto dto) {
        model.setName(dto.getName());
        model.setLevel(dto.getLevel());
        model.setLongitude(dto.getLongitude());
        model.setLatitude(dto.getLatitude());
        model.setAddress(dto.getAddress());
        model.setMedicalInsuranceTel(dto.getMedicalInsuranceTel());
        model.setDescription(dto.getDescription());
        model.setWorkingTime(dto.getWorkingTime());
        model.setState(dto.getState());
        if (!ObjectUtils.isEmpty(dto.getDepartments())) {
            model.setDepartmentsName(
                    dto.getDepartments().stream().map(DepartmentDto::getName).collect(Collectors.joining(",")));
        }
        model.setInId(dto.getInId());
        return model;
    }

}
