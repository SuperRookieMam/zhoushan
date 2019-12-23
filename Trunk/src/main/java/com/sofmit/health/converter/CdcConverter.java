package com.sofmit.health.converter;

import com.dm.common.converter.AbstractConverter;
import com.dm.file.converter.FileInfoConverter;
import com.sofmit.health.dto.CdcDto;
import com.sofmit.health.dto.VaccineDto;
import com.sofmit.health.entity.Cdc;
import org.apache.commons.collections4.IterableUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
public class CdcConverter extends AbstractConverter<Cdc, CdcDto> {
    @Autowired
    private FileInfoConverter fileInfoConverter;
    @Autowired
    private VaccineConverter vaccineConverter;

    @Override
    protected CdcDto toDtoActual(Cdc model) {
        CdcDto dto = new CdcDto();
        dto.setId(model.getId());
        dto.setName(model.getName());
        dto.setLongitude(model.getLongitude());
        dto.setLatitude(model.getLatitude());
        dto.setAddress(model.getAddress());
        dto.setTel(model.getTel());
        dto.setDescription(model.getDescription());
        dto.setInstitution(model.getInstitution());
        dto.setLogo(fileInfoConverter.toDto(model.getLogo()).orElse(null));
        dto.setImgs(fileInfoConverter.toDto(model.getImgs()));
        dto.setVaccines(vaccineConverter.toDto(model.getVaccines()));
        dto.setState(model.getState());
        dto.setCreatedDate(model.getCreatedDate());
        dto.setLastModifiedDate(model.getLastModifiedDate());
        dto.setVaccinesName(model.getVaccinesName());
        dto.setWorkingTime(model.getWorkingTime());
        return dto;
    }

    public CdcDto toList(Cdc model) {
        CdcDto dto = new CdcDto();
        dto.setId(model.getId());
        dto.setName(model.getName());
        dto.setLongitude(model.getLongitude());
        dto.setLatitude(model.getLatitude());
        dto.setAddress(model.getAddress());
        dto.setTel(model.getTel());
        dto.setInstitution(model.getInstitution());
        dto.setLogo(fileInfoConverter.toDto(model.getLogo()).orElse(null));
        dto.setImgs(fileInfoConverter.toDto(model.getImgs()));
        dto.setVaccines(vaccineConverter.toDto(model.getVaccines()));
        dto.setState(model.getState());
        dto.setCreatedDate(model.getCreatedDate());
        dto.setLastModifiedDate(model.getLastModifiedDate());
        dto.setVaccinesName(model.getVaccinesName());
        dto.setWorkingTime(model.getWorkingTime());
        return dto;
    }

    public List<CdcDto> toList(Iterable<Cdc> models) {
        return IterableUtils.isEmpty(models) ? Collections.emptyList()
                : (List) StreamSupport.stream(models.spliterator(), false).map(this::toList)
                        .collect(Collectors.toList());
    }

    @Override
    public Cdc copyProperties(Cdc model, CdcDto dto) {
        model.setName(dto.getName());
        model.setLongitude(dto.getLongitude());
        model.setLatitude(dto.getLatitude());
        model.setAddress(dto.getAddress());
        model.setTel(dto.getTel());
        model.setDescription(dto.getDescription());
        model.setInstitution(dto.getInstitution());
        model.setState(dto.getState());
        model.setWorkingTime(dto.getWorkingTime());
        if (!ObjectUtils.isEmpty(dto.getVaccines()))
            model.setVaccinesName(dto.getVaccines().stream().map(VaccineDto::getName).collect(Collectors.joining(",")));
        return model;
    }
}
