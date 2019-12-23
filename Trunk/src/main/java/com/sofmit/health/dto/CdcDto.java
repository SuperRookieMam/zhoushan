package com.sofmit.health.dto;

import com.dm.file.dto.FileInfoDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class CdcDto extends AbstractDto<Long> implements Serializable {

    private static final long serialVersionUID = 1L;
    private String name;
    private Double longitude;
    private Double latitude;
    private String address;
    private String tel;
    private String description;
    private String institution;
    private FileInfoDto logo;
    private List<FileInfoDto> imgs;
    private List<VaccineDto> vaccines;
    private String vaccinesName;
    private String workingTime;
}
