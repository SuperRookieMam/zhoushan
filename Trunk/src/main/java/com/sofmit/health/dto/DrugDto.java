package com.sofmit.health.dto;

import com.dm.file.dto.FileInfoDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class DrugDto extends AbstractDto<Long> implements Serializable {

    private static final long serialVersionUID = 1L;
    private String number;
    private String name;
    private String factory;
    private String standard;
    private String dosageForm;
    private Double price;
    private String selfConceit;
    private String detail;
    private List<FileInfoDto> imgs;
    private Double inJobSelfConceit;
    private Double dimissionSelfConceit;
    private Double retireSelfConceit;
    private String qualifiedToPay;
    private String inId;
}
