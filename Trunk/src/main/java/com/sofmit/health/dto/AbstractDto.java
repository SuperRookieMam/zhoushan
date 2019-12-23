package com.sofmit.health.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.ZonedDateTime;

import com.dm.common.dto.IdentifiableDto;

@Data
public class AbstractDto<ID extends Serializable> implements IdentifiableDto<ID> {
    private ID id;

    private ZonedDateTime createdDate;

    private ZonedDateTime lastModifiedDate;

    private String state;
}
