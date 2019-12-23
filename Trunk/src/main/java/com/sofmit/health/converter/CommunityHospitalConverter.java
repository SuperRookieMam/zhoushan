package com.sofmit.health.converter;

import com.dm.common.converter.AbstractConverter;
import com.dm.file.converter.FileInfoConverter;
import com.sofmit.health.dto.CommunityHospitalDto;
import com.sofmit.health.dto.VaccineDto;
import com.sofmit.health.entity.CommunityHospital;
import org.apache.commons.collections4.IterableUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
public class CommunityHospitalConverter extends AbstractConverter<CommunityHospital, CommunityHospitalDto> {

    @Autowired
    private FileInfoConverter fileInfoConverter;

    @Autowired
    private VaccineConverter vc;

    @Override
    protected CommunityHospitalDto toDtoActual(CommunityHospital model) {
        CommunityHospitalDto dto = new CommunityHospitalDto();
        dto.setId(model.getId());
        dto.setName(model.getName());
        dto.setTel(model.getTel());
        dto.setLongitude(model.getLongitude());
        dto.setLatitude(model.getLatitude());
        dto.setAddress(model.getAddress());
        dto.setPics(fileInfoConverter.toDto(model.getPics()));
        dto.setLogo(fileInfoConverter.toDto(model.getLogo()).orElse(null));
        dto.setVaccines(vc.toDto(model.getVaccines()));
        dto.setState(model.getState());
        dto.setCreatedDate(model.getCreatedDate());
        dto.setLastModifiedDate(model.getLastModifiedDate());
        dto.setType(model.getType());
        dto.setServiceTime(model.getServiceTime());
        dto.setDetail(model.getDetail());
        dto.setVaccinesName(model.getVaccinesName());
        dto.setInId(model.getInId());
        return dto;
    }

    public CommunityHospitalDto toList(CommunityHospital model) {
        CommunityHospitalDto dto = new CommunityHospitalDto();
        dto.setId(model.getId());
        dto.setName(model.getName());
        dto.setTel(model.getTel());
        dto.setLongitude(model.getLongitude());
        dto.setLatitude(model.getLatitude());
        dto.setAddress(model.getAddress());
        dto.setPics(fileInfoConverter.toDto(model.getPics()));
        dto.setLogo(fileInfoConverter.toDto(model.getLogo()).orElse(null));
        dto.setVaccines(vc.toDto(model.getVaccines()));
        dto.setState(model.getState());
        dto.setCreatedDate(model.getCreatedDate());
        dto.setLastModifiedDate(model.getLastModifiedDate());
        dto.setType(model.getType());
        dto.setServiceTime(model.getServiceTime());
        dto.setVaccinesName(model.getVaccinesName());
        dto.setInId(model.getInId());
        return dto;
    }

    public List<CommunityHospitalDto> toList(Iterable<CommunityHospital> models) {
        return IterableUtils.isEmpty(models) ? Collections.emptyList()
                : (List) StreamSupport.stream(models.spliterator(), false).map(this::toList)
                        .collect(Collectors.toList());
    }

    @Override
    public CommunityHospital copyProperties(CommunityHospital model, CommunityHospitalDto dto) {
        model.setName(dto.getName());
        model.setTel(dto.getTel());
        model.setLongitude(dto.getLongitude());
        model.setLatitude(dto.getLatitude());
        model.setAddress(dto.getAddress());
        model.setServiceTime(dto.getServiceTime());
        model.setState(dto.getState());
        model.setType(dto.getType());
        model.setDetail(dto.getDetail());
        model.setInId(dto.getInId());
        if (!ObjectUtils.isEmpty(dto.getVaccines())) {
            String vaccinesName = dto.getVaccines().stream().map(VaccineDto::getName).collect(Collectors.joining(","));
            model.setVaccinesName(vaccinesName);
        }
        return model;
    }
}
