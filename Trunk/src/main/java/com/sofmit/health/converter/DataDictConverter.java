package com.sofmit.health.converter;

import org.springframework.stereotype.Component;

import com.dm.common.converter.AbstractConverter;
import com.sofmit.health.dto.DataDictDto;
import com.sofmit.health.entity.DataDict;

@Component
public class DataDictConverter extends AbstractConverter<DataDict, DataDictDto> {

    @Override
    protected DataDictDto toDtoActual(DataDict model) {
        DataDictDto dto = new DataDictDto();
        dto.setId(model.getId());
        dto.setName(model.getName());
        dto.setType(model.getType());
        dto.setCode(model.getCode());
        return dto;
    }

    @Override
    public DataDict copyProperties(DataDict model, DataDictDto dto) {
        model.setName(dto.getName());
        model.setType(dto.getType());
        model.setCode(dto.getCode());
        return model;
    }
}