package com.sofmit.health.entity;

import com.dm.common.entity.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 *
 * 疫苗分类
 *
 * @author 李东
 * @since
 * @Copyright (C) 2019 江苏云智网络科技股份有限公司版权所有
 *
 */
@Entity(name = "enum_vaccine_type_")
@Getter
@Setter
public class VaccineType extends AbstractEntity {

    private static final long serialVersionUID = -3375513493137764165L;

    /**
     * 名称
     */
    @Column(name = "name_", length = 100)
    private String name;

    /**
     * 上级分类
     */
    @ManyToOne
    @JoinColumn(name = "parent_")
    private VaccineType parent;

}
