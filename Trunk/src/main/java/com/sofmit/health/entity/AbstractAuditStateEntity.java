package com.sofmit.health.entity;

import java.time.ZonedDateTime;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.dm.common.entity.AbstractEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class AbstractAuditStateEntity extends AbstractEntity {

    private static final long serialVersionUID = -6172980315482653295L;

    @CreatedDate
    @Column(name = "created_date_")
    private ZonedDateTime createdDate;

    @LastModifiedDate
    @Column(name = "last_modified_date_")
    private ZonedDateTime lastModifiedDate;

    @Column(name = "state_")
    private String state;
}
