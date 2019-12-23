package com.sofmit.health.converter;

import com.dm.common.converter.AbstractConverter;
import com.dm.file.converter.FileInfoConverter;
import com.sofmit.health.dto.DrugDto;
import com.sofmit.health.entity.Drug;
import org.apache.commons.collections4.IterableUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
public class DrugConverter extends AbstractConverter<Drug, DrugDto> {
    @Autowired
    private FileInfoConverter fileInfoConverter;

    @Override
    protected DrugDto toDtoActual(Drug model) {
        DrugDto dto = new DrugDto();
        dto.setId(model.getId());
        dto.setNumber(model.getNumber());
        dto.setName(model.getName());
        dto.setFactory(model.getFactory());
        dto.setStandard(model.getStandard());
        dto.setDosageForm(model.getDosageForm());
        dto.setPrice(model.getPrice());
        dto.setSelfConceit(model.getSelfConceit());
        dto.setDetail(model.getDetail());
        dto.setImgs(fileInfoConverter.toDto(model.getImgs()));
        dto.setState(model.getState());
        dto.setCreatedDate(model.getCreatedDate());
        dto.setLastModifiedDate(model.getLastModifiedDate());
        dto.setDimissionSelfConceit(model.getDimissionSelfConceit());
        dto.setRetireSelfConceit(model.getRetireSelfConceit());
        dto.setInJobSelfConceit(model.getInJobSelfConceit());
        dto.setQualifiedToPay(model.getQualifiedToPay());
        dto.setInId(model.getInId());
        return dto;
    }

    public DrugDto toList(Drug model) {
        DrugDto dto = new DrugDto();
        dto.setId(model.getId());
        dto.setNumber(model.getNumber());
        dto.setName(model.getName());
        dto.setFactory(model.getFactory());
        dto.setStandard(model.getStandard());
        dto.setDosageForm(model.getDosageForm());
        dto.setPrice(model.getPrice());
        dto.setSelfConceit(model.getSelfConceit());
        dto.setImgs(fileInfoConverter.toDto(model.getImgs()));
        dto.setState(model.getState());
        dto.setCreatedDate(model.getCreatedDate());
        dto.setLastModifiedDate(model.getLastModifiedDate());
        dto.setDimissionSelfConceit(model.getDimissionSelfConceit());
        dto.setRetireSelfConceit(model.getRetireSelfConceit());
        dto.setInJobSelfConceit(model.getInJobSelfConceit());
        dto.setQualifiedToPay(model.getQualifiedToPay());
        dto.setInId(model.getInId());
        return dto;
    }

    public List<DrugDto> toList(Iterable<Drug> models) {
        return IterableUtils.isEmpty(models) ? Collections.emptyList()
                : (List) StreamSupport.stream(models.spliterator(), false).map(this::toList)
                        .collect(Collectors.toList());
    }

    @Override
    public Drug copyProperties(Drug model, DrugDto dto) {
        model.setNumber(dto.getNumber());
        model.setName(dto.getName());
        model.setFactory(dto.getFactory());
        model.setStandard(dto.getStandard());
        model.setDosageForm(dto.getDosageForm());
        model.setPrice(dto.getPrice());
        model.setSelfConceit(dto.getSelfConceit());
        model.setDetail(dto.getDetail());
        model.setState(dto.getState());
        model.setDimissionSelfConceit(dto.getDimissionSelfConceit());
        model.setRetireSelfConceit(dto.getRetireSelfConceit());
        model.setInJobSelfConceit(dto.getInJobSelfConceit());
        model.setQualifiedToPay(dto.getQualifiedToPay());
        model.setInId(dto.getInId());
        return model;
    }
}
