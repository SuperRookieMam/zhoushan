package com.sofmit.health.entity;

import com.dm.file.entity.FileInfo;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * 药店
 *
 * @author 叶洪亮
 * @since
 * @Copyright (C) 2019 江苏云智网络科技股份有限公司版权所有
 *
 */
@Getter
@Setter
@Entity(name = "zs_drugstore_")
public class Drugstore extends AbstractAuditStateEntity implements Serializable {
    private static final long serialVersionUID = 5035504773543187367L;

    /**
     * 药店名称
     */
    @Column(name = "name_")
    private String name;

    /**
     * 营业时间
     */
    @Column(name = "dopening_time_")
    private String openingTime;

    /**
     * 电话
     */
    @Column(name = "tel_")
    private String tel;
    /**
     * 地址
     */
    @Column(name = "adress_")
    private String address;

    /**
     * 经度
     */
    @Column(name = "longitude_")
    private Double longitude;

    /**
     * 纬度
     */
    @Column(name = "latitude_")
    private Double latitude;

    /**
     * 详情
     */
    @Lob
    @Column(name = "detail_")
    @Basic(fetch = FetchType.LAZY)
    private String detail;

    /**
     * 药店
     */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "zs_drugstore_imgs_")
    private List<FileInfo> imgs;
    /**
     * 接入数据的的编号，以此判断此数据是否重复
     */
    @Column(name = "in_id_")
    private String inId;
}
