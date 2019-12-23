package com.sofmit.health.dto;

import com.dm.file.dto.FileInfoDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class PreventionDiseasesDto extends AbstractDto<Long> implements Serializable {

    private static final long serialVersionUID = 1L;
    private String type;
    private String title;
    private String source;
    private FileInfoDto coverImg;
    private String content;
    private List<FileInfoDto> accessory;
    private Integer readNumber = 0;
}
