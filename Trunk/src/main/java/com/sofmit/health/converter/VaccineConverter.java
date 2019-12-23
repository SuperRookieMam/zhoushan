package com.sofmit.health.converter;

import org.springframework.stereotype.Component;

import com.dm.common.converter.AbstractConverter;
import com.sofmit.health.dto.VaccineDto;
import com.sofmit.health.entity.Vaccine;

@Component
public class VaccineConverter extends AbstractConverter<Vaccine, VaccineDto> {

    @Override
    protected VaccineDto toDtoActual(Vaccine model) {
        VaccineDto dto = new VaccineDto();
        dto.setId(model.getId());
        dto.setName(model.getName());
        dto.setType(model.getType());
        dto.setPaymentMode(model.getPaymentMode());
        dto.setState(model.getState());
        dto.setCreatedDate(model.getCreatedDate());
        dto.setLastModifiedDate(model.getLastModifiedDate());
        return dto;
    }

    @Override
    public Vaccine copyProperties(Vaccine model, VaccineDto dto) {
        model.setName(dto.getName());
        model.setType(dto.getType());
        model.setPaymentMode(dto.getPaymentMode());
        model.setState(dto.getState());
        return model;
    }
}
