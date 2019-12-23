package com.sofmit.health.converter;

import org.springframework.stereotype.Component;

import com.dm.common.converter.AbstractConverter;
import com.sofmit.health.dto.VaccineTypeDto;
import com.sofmit.health.entity.VaccineType;

@Component
public class VaccineTypeConverter extends AbstractConverter<VaccineType, VaccineTypeDto> {

    @Override
    protected VaccineTypeDto toDtoActual(VaccineType model) {
        VaccineTypeDto dto = new VaccineTypeDto();
        dto.setId(model.getId());
        dto.setName(model.getName());
        dto.setParent(toDto(model.getParent()).orElse(null));
        return dto;
    }

    @Override
    public VaccineType copyProperties(VaccineType model, VaccineTypeDto dto) {
        model.setName(dto.getName());
        return model;
    }
}
