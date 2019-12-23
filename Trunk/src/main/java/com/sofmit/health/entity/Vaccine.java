package com.sofmit.health.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 *
 * 疫苗实体
 *
 * @author 李东
 * @since
 * @Copyright (C) 2019 江苏云智网络科技股份有限公司版权所有
 *
 */
@Entity(name = "zs_vaccine_")
@Getter
@Setter
public class Vaccine extends AbstractAuditStateEntity {

    private static final long serialVersionUID = -3375513493137764165L;

    /**
     * 名称
     */
    @Column(name = "name_", length = 100)
    private String name;

    /**
     * 类型
     */
    @Column(name = "type_", length = 100)
    private String type;

    /**
     * 付费模式
     */
    @Column(name = "payment_mode_")
    private String paymentMode;

}
