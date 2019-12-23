package com.sofmit.health.entity;

import com.dm.file.entity.FileInfo;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

/**
 * 社区医院。医保中心
 *
 * @author 李东
 * @Copyright (C) 2019 江苏云智网络科技股份有限公司版权所有
 * @since
 */
@Entity(name = "zs_community_hospital_")
@Getter
@Setter
public class CommunityHospital extends AbstractAuditStateEntity {

    private static final long serialVersionUID = 790896905043023270L;

    /**
     * 类型
     */
    @Column(name = "type_", length = 100)
    private String type;

    /**
     * 名称
     */
    @Column(name = "name_", length = 200)
    private String name;

    /**
     * 电话
     */
    @Column(name = "tel_", length = 50)
    private String tel;
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
     * 地址
     */
    @Column(name = "address_", length = 200)
    private String address;

    /**
     * 服务时间
     */
    private String serviceTime;

    /**
     * 详情
     */
    @Lob
    @Column(name = "detail_")
    @Basic(fetch = FetchType.LAZY)
    private String detail;

    /**
     * 图片
     */
    @OneToMany(cascade = CascadeType.REMOVE)
    @JoinTable(name = "zs_community_hospital_pics_")
    private List<FileInfo> pics;

    /**
     * logo信息
     */
    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "logo_id_")
    private FileInfo logo;

    /**
     * 医院可以接种的疫苗
     */
    @ManyToMany
    @JoinTable(name = "zs_community_hospital_vaccines_")
    private List<Vaccine> vaccines;

    @Lob
    @Column(name = "vaccines_name_")
    @Basic(fetch = FetchType.LAZY)
    private String vaccinesName;

    /**
     * 接入数据的的编号，以此判断此数据是否重复
     */
    @Column(name = "in_id_")
    private String inId;
}
