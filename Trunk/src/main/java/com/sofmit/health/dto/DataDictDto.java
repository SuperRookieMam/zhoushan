package com.sofmit.health.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
public class DataDictDto extends AbstractDto<Long> implements Serializable {

    private static final long serialVersionUID = 1L;
    private String name;
    private String type;
    private String code;
}
