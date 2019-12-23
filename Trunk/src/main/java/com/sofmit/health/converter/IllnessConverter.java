package com.sofmit.health.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dm.common.converter.AbstractConverter;
import com.sofmit.health.dto.IllnessDto;
import com.sofmit.health.entity.Illness;
import org.springframework.util.ObjectUtils;

import java.util.stream.Collectors;

@Component
public class IllnessConverter extends AbstractConverter<Illness, IllnessDto> {
    @Autowired
    private DepartmentConverter departmentConverter;

    @Override
    protected IllnessDto toDtoActual(Illness model) {
        IllnessDto dto = new IllnessDto();
        dto.setId(model.getId());
        dto.setName(model.getName());
        dto.setBodyPart(model.getBodyPart());
        dto.setCrowd(model.getCrowd());
        dto.setClinicDepartment(model.getClinicDepartment());
        dto.setState(model.getState());
        dto.setCreatedDate(model.getCreatedDate());
        dto.setLastModifiedDate(model.getLastModifiedDate());
        dto.setAgeGroup(model.getAgeGroup());
        dto.setDepartments(departmentConverter.toDto(model.getDepartments()));
        return dto;
    }

    @Override
    public Illness copyProperties(Illness model, IllnessDto dto) {
        model.setName(dto.getName());
        model.setBodyPart(dto.getBodyPart());
        model.setCrowd(dto.getCrowd());
        model.setState(dto.getState());
        model.setAgeGroup(dto.getAgeGroup());
        if (!ObjectUtils.isEmpty(dto.getDepartments())) {
            model.setClinicDepartment(dto.getDepartments().stream().map(ele -> {
                return ele.getName();
            }).collect(Collectors.joining(",")));
        }
        return model;
    }
}
