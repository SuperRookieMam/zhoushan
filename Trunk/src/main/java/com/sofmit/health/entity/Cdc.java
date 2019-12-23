package com.sofmit.health.entity;

import com.dm.file.entity.FileInfo;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * 疾控中心
 */
@Getter
@Setter
@Entity(name = "zs_cdc_")
public class Cdc extends AbstractAuditStateEntity implements Serializable {
    private static final long serialVersionUID = 3359188716152909373L;

    /**
     * 医院名称
     */
    @Column(name = "name_")
    private String name;
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
    @Column(name = "address_")
    private String address;

    /**
     * 医保办电话
     **/
    @Column(name = "tel_")
    private String tel;

    /**
     * 简介
     **/
    @Lob
    @Column(name = "description_")
    @Basic(fetch = FetchType.LAZY)
    private String description;

    /**
     * 机构设置
     **/
    @Lob
    @Column(name = "institution_")
    @Basic(fetch = FetchType.LAZY)
    private String institution;

    /**
     * 医院logo
     */
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "logo_")
    private FileInfo logo;

    /**
     * 图
     */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "zs_cdc_imgs_", joinColumns = {
            @JoinColumn(name = "zs_cdc_id_")
    }, inverseJoinColumns = {
            @JoinColumn(name = "dm_file_id_")
    })
    private List<FileInfo> imgs;

    /**
     * 医院可以接种的疫苗
     */
    @ManyToMany
    @JoinTable(name = "zs_cdc_vaccines_")
    private List<Vaccine> vaccines;

    @Lob
    @Column(name = "vaccines_name_")
    @Basic(fetch = FetchType.LAZY)
    private String vaccinesName;

    @Column(name = "working_time_")
    private String workingTime;
}
