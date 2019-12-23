package com.sofmit.health.dto;

import com.dm.file.dto.FileInfoDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class PrivateDoctorDto extends AbstractDto<Long> implements Serializable {

    private static final long serialVersionUID = 1L;
    private String number;
    private String name;
    private String anotherName;
    private DepartmentDto department;
    private String summarize;
    private String symptom;
    private String pathogen;
    private String medicalAdvice;
    private String treat;
    private String prognosis;
    private String general;
    private List<FileInfoDto> imgs;
    private Integer releaseState;
    private String departmentsName;
}
