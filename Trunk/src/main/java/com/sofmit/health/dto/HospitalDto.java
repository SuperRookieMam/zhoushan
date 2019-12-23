package com.sofmit.health.dto;

import com.dm.file.dto.FileInfoDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class HospitalDto extends AbstractDto<Long> implements Serializable {

    private static final long serialVersionUID = 1L;
    private String name;
    private String level;
    private Double longitude;
    private Double latitude;
    private String address;
    private String medicalInsuranceTel;
    private String description;
    private FileInfoDto logo;
    private List<FileInfoDto> imgs;
    private List<DepartmentDto> departments;
    private String workingTime;
    private String departmentsName;
    private String inId;
}
