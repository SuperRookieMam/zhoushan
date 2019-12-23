package com.sofmit.health.converter;

import com.dm.common.converter.AbstractConverter;
import com.dm.file.converter.FileInfoConverter;
import com.sofmit.health.dto.PreventionDiseasesDto;
import com.sofmit.health.entity.PreventionDiseases;
import org.apache.commons.collections4.IterableUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
public class PreventionDiseasesConverter extends AbstractConverter<PreventionDiseases, PreventionDiseasesDto> {
    @Autowired
    private FileInfoConverter fileInfoConverter;

    @Override
    protected PreventionDiseasesDto toDtoActual(PreventionDiseases model) {
        PreventionDiseasesDto dto = new PreventionDiseasesDto();
        dto.setId(model.getId());
        dto.setType(model.getType());
        dto.setTitle(model.getTitle());
        dto.setSource(model.getSource());
        dto.setCoverImg(fileInfoConverter.toDto(model.getCoverImg()).orElse(null));
        dto.setContent(model.getContent());
        dto.setAccessory(fileInfoConverter.toDto(model.getAccessory()));
        dto.setState(model.getState());
        dto.setCreatedDate(model.getCreatedDate());
        dto.setLastModifiedDate(model.getLastModifiedDate());
        dto.setReadNumber(model.getReadNumber());
        return dto;
    }

    public PreventionDiseasesDto toList(PreventionDiseases model) {
        PreventionDiseasesDto dto = new PreventionDiseasesDto();
        dto.setId(model.getId());
        dto.setType(model.getType());
        dto.setTitle(model.getTitle());
        dto.setSource(model.getSource());
        dto.setCoverImg(fileInfoConverter.toDto(model.getCoverImg()).orElse(null));
        dto.setAccessory(fileInfoConverter.toDto(model.getAccessory()));
        dto.setState(model.getState());
        dto.setCreatedDate(model.getCreatedDate());
        dto.setLastModifiedDate(model.getLastModifiedDate());
        dto.setReadNumber(model.getReadNumber());
        return dto;
    }

    public List<PreventionDiseasesDto> toList(Iterable<PreventionDiseases> models) {
        return IterableUtils.isEmpty(models) ? Collections.emptyList()
                : (List) StreamSupport.stream(models.spliterator(), false).map(this::toList)
                        .collect(Collectors.toList());
    }

    @Override
    public PreventionDiseases copyProperties(PreventionDiseases model, PreventionDiseasesDto dto) {
        model.setType(dto.getType());
        model.setTitle(dto.getTitle());
        model.setSource(dto.getSource());
        model.setContent(dto.getContent());
        model.setState(dto.getState());
        model.setReadNumber(dto.getReadNumber());
        return model;
    }
}
