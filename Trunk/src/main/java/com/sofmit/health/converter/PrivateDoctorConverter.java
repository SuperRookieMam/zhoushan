package com.sofmit.health.converter;

import com.dm.common.converter.AbstractConverter;
import com.dm.file.converter.FileInfoConverter;
import com.sofmit.health.dto.PrivateDoctorDto;
import com.sofmit.health.entity.PrivateDoctor;
import org.apache.commons.collections4.IterableUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
public class PrivateDoctorConverter extends AbstractConverter<PrivateDoctor, PrivateDoctorDto> {
    @Autowired
    private DepartmentConverter departmentConverter;
    @Autowired
    private FileInfoConverter fileInfoConverter;

    @Override
    protected PrivateDoctorDto toDtoActual(PrivateDoctor model) {
        PrivateDoctorDto dto = new PrivateDoctorDto();
        dto.setId(model.getId());
        dto.setNumber(model.getNumber());
        dto.setName(model.getName());
        dto.setAnotherName(model.getAnotherName());
        dto.setDepartment(departmentConverter.toDto(model.getDepartment()).orElse(null));
        dto.setSummarize(model.getSummarize());
        dto.setSymptom(model.getSymptom());
        dto.setPathogen(model.getPathogen());
        dto.setMedicalAdvice(model.getMedicalAdvice());
        dto.setTreat(model.getTreat());
        dto.setPrognosis(model.getPrognosis());
        dto.setGeneral(model.getGeneral());
        dto.setImgs(fileInfoConverter.toDto(model.getImgs()));
        dto.setReleaseState(model.getReleaseState());
        dto.setState(model.getState());
        dto.setCreatedDate(model.getCreatedDate());
        dto.setLastModifiedDate(model.getLastModifiedDate());
        dto.setDepartmentsName(model.getDepartmentsName());
        return dto;
    }

    public PrivateDoctorDto toList(PrivateDoctor model) {
        PrivateDoctorDto dto = new PrivateDoctorDto();
        dto.setId(model.getId());
        dto.setNumber(model.getNumber());
        dto.setName(model.getName());
        dto.setAnotherName(model.getAnotherName());
        dto.setDepartment(departmentConverter.toDto(model.getDepartment()).orElse(null));
         dto.setSummarize(model.getSummarize());
        // dto.setSymptom(model.getSymptom());
        // dto.setPathogen(model.getPathogen());
        // dto.setMedicalAdvice(model.getMedicalAdvice());
        // dto.setTreat(model.getTreat());
        // dto.setPrognosis(model.getPrognosis());
        // dto.setGeneral(model.getGeneral());
        dto.setImgs(fileInfoConverter.toDto(model.getImgs()));
        dto.setReleaseState(model.getReleaseState());
        dto.setState(model.getState());
        dto.setCreatedDate(model.getCreatedDate());
        dto.setLastModifiedDate(model.getLastModifiedDate());
        dto.setDepartmentsName(model.getDepartmentsName());
        return dto;
    }

    public List<PrivateDoctorDto> toList(Iterable<PrivateDoctor> models) {
        return IterableUtils.isEmpty(models) ? Collections.emptyList()
                : (List) StreamSupport.stream(models.spliterator(), false).map(this::toList)
                        .collect(Collectors.toList());
    }

    @Override
    public PrivateDoctor copyProperties(PrivateDoctor model, PrivateDoctorDto dto) {
        model.setNumber(dto.getNumber());
        model.setName(dto.getName());
        model.setAnotherName(dto.getAnotherName());
        model.setSummarize(dto.getSummarize());
        model.setSymptom(dto.getSymptom());
        model.setPathogen(dto.getPathogen());
        model.setMedicalAdvice(dto.getMedicalAdvice());
        model.setTreat(dto.getTreat());
        model.setPrognosis(dto.getPrognosis());
        model.setGeneral(dto.getGeneral());
        model.setReleaseState(dto.getReleaseState());
        model.setState(dto.getState());
        String departName = "";
        if (!ObjectUtils.isEmpty(dto.getDepartment())) {
            departName = dto.getDepartment().getName();
        }
        model.setDepartmentsName(departName);

        return model;
    }
}
