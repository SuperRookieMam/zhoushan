package com.sofmit.health.converter;

import com.dm.common.converter.AbstractConverter;
import com.dm.file.converter.FileInfoConverter;
import com.sofmit.health.dto.DepartmentDto;
import com.sofmit.health.dto.HospitalDto;
import com.sofmit.health.entity.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.stream.Collectors;

@Component("hDepartmentConverter")
public class DepartmentConverter extends AbstractConverter<Department, DepartmentDto> {
    @Autowired
    private FileInfoConverter fileInfoConverter;
    @Autowired
    private HospitalConverter hospitalConverter;

    @Override
    protected DepartmentDto toDtoActual(Department model) {
        DepartmentDto dto = new DepartmentDto();
        dto.setId(model.getId());
        dto.setName(model.getName());
        dto.setPid(model.getPid());
        dto.setPname(model.getPname());
        // dto.setHospitals(hospitalConverter.toDto(model.getHospitals()));
        if (!ObjectUtils.isEmpty(model.getHospitals()))
            dto.setHospitals(model.getHospitals().stream().map(ele -> {
                HospitalDto hospitalDto = new HospitalDto();
                hospitalDto.setId(ele.getId());
                hospitalDto.setName(ele.getName());
                hospitalDto.setLevel(ele.getLevel());
                hospitalDto.setLongitude(ele.getLongitude());
                hospitalDto.setLatitude(ele.getLatitude());
                hospitalDto.setAddress(ele.getAddress());
                hospitalDto.setMedicalInsuranceTel(ele.getMedicalInsuranceTel());
                hospitalDto.setDescription(ele.getDescription());
                hospitalDto.setLogo(fileInfoConverter.toDto(ele.getLogo()).orElse(null));
                hospitalDto.setImgs(fileInfoConverter.toDto(ele.getImgs()));
                hospitalDto.setWorkingTime(ele.getWorkingTime());
                hospitalDto.setCreatedDate(ele.getCreatedDate());
                hospitalDto.setLastModifiedDate(ele.getLastModifiedDate());
                hospitalDto.setState(ele.getState());
                hospitalDto.setCreatedDate(ele.getCreatedDate());
                hospitalDto.setLastModifiedDate(ele.getLastModifiedDate());
                hospitalDto.setDepartmentsName(ele.getDepartmentsName());
                return hospitalDto;
            }).collect(Collectors.toList()));
        dto.setState(model.getState());
        dto.setCreatedDate(model.getCreatedDate());
        dto.setLastModifiedDate(model.getLastModifiedDate());
        return dto;
    }

    @Override
    public Department copyProperties(Department model, DepartmentDto dto) {
        model.setName(dto.getName());
        model.setPid(dto.getPid());
        model.setPname(dto.getPname());
        model.setState(dto.getState());
        return model;
    }
}
