package com.sofmit.health.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class IllnessDto extends AbstractDto<Long> implements Serializable {

    private static final long serialVersionUID = 1L;
    private String name;
    private String bodyPart;
    private String crowd;
    private String ageGroup;
    private String clinicDepartment;
    private List<DepartmentDto> departments;
}
