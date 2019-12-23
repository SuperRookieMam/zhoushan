package com.sofmit.health.converter;

import com.dm.common.converter.AbstractConverter;
import com.dm.file.converter.FileInfoConverter;
import com.sofmit.health.dto.DrugstoreDto;
import com.sofmit.health.entity.Drugstore;
import org.apache.commons.collections4.IterableUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
public class DrugstoreConverter extends AbstractConverter<Drugstore, DrugstoreDto> {
    @Autowired
    private FileInfoConverter fileInfoConverter;

    @Override
    protected DrugstoreDto toDtoActual(Drugstore model) {
        DrugstoreDto dto = new DrugstoreDto();
        dto.setId(model.getId());
        dto.setName(model.getName());
        dto.setOpeningTime(model.getOpeningTime());
        dto.setTel(model.getTel());
        dto.setAddress(model.getAddress());
        dto.setLongitude(model.getLongitude());
        dto.setLatitude(model.getLatitude());
        dto.setDetail(model.getDetail());
        dto.setImgs(fileInfoConverter.toDto(model.getImgs()));
        dto.setState(model.getState());
        dto.setCreatedDate(model.getCreatedDate());
        dto.setLastModifiedDate(model.getLastModifiedDate());
        dto.setInId(model.getInId());
        return dto;
    }

    public DrugstoreDto toList(Drugstore model) {
        DrugstoreDto dto = new DrugstoreDto();
        dto.setId(model.getId());
        dto.setName(model.getName());
        dto.setOpeningTime(model.getOpeningTime());
        dto.setTel(model.getTel());
        dto.setAddress(model.getAddress());
        dto.setLongitude(model.getLongitude());
        dto.setLatitude(model.getLatitude());
        dto.setImgs(fileInfoConverter.toDto(model.getImgs()));
        dto.setState(model.getState());
        dto.setCreatedDate(model.getCreatedDate());
        dto.setLastModifiedDate(model.getLastModifiedDate());
        dto.setInId(model.getInId());
        return dto;
    }

    public List<DrugstoreDto> toList(Iterable<Drugstore> models) {
        return IterableUtils.isEmpty(models) ? Collections.emptyList()
                : (List) StreamSupport.stream(models.spliterator(), false).map(this::toList)
                        .collect(Collectors.toList());
    }

    @Override
    public Drugstore copyProperties(Drugstore model, DrugstoreDto dto) {
        model.setName(dto.getName());
        model.setOpeningTime(dto.getOpeningTime());
        model.setTel(dto.getTel());
        model.setAddress(dto.getAddress());
        model.setLongitude(dto.getLongitude());
        model.setLatitude(dto.getLatitude());
        model.setDetail(dto.getDetail());
        model.setState(dto.getState());
        model.setInId(dto.getInId());
        return model;
    }
}
