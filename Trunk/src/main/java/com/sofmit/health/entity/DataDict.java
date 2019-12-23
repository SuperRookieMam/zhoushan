package com.sofmit.health.entity;

import com.dm.common.entity.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.io.Serializable;

/**
 * 字典表
 */
@Getter
@Setter
@Entity(name = "zs_data_dict_")
public class DataDict extends AbstractEntity implements Serializable {
    private static final long serialVersionUID = -131629887282664963L;

    /**
     * 字典名称
     */
    @Column(name = "name_")
    private String name;

    /**
     * 字典类型
     */
    @Column(name = "type_")
    private String type;

    /**
     * 字典code
     */
    @Column(name = "code_")
    private String code;

}
