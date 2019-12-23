package com.sofmit.health.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class DepartmentDto extends AbstractDto<Long> implements Serializable {

    private static final long serialVersionUID = 1L;
    private String name;
    private Long pid = 0L;
    private String pname;
    private List<HospitalDto> hospitals;
}
