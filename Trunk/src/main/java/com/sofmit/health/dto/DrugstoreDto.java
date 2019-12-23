package com.sofmit.health.dto;

import com.dm.file.dto.FileInfoDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class DrugstoreDto extends AbstractDto<Long> implements Serializable {

    private static final long serialVersionUID = 1L;
    private String name;
    private String openingTime;
    private String tel;
    private String address;
    private Double longitude;
    private Double latitude;
    private String detail;
    private List<FileInfoDto> imgs;
    private String inId;
}
