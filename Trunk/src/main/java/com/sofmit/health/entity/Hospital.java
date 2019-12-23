package com.sofmit.health.entity;

import com.dm.file.entity.FileInfo;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * 医院信息
 */
@Getter
@Setter
@Entity(name = "zs_hospital_")
public class Hospital extends AbstractAuditStateEntity implements Serializable {
    private static final long serialVersionUID = 1918226215944434114L;
    /**
     * 医院名称
     */
    @Column(name = "name_")
    private String name;
    /**
     * 医院等级
     */
    @Column(name = "level_")
    private String level;

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
     * 医院地址
     */
    @Column(name = "adress_")
    private String address;

    /**
     * 医保办电话
     **/
    @Column(name = "medical_insurance_tel_")
    private String medicalInsuranceTel;

    /**
     * 医院简介
     **/
    @Lob
    @Column(name = "description_")
    @Basic(fetch = FetchType.LAZY)
    private String description;

    /**
     * 医院logo
     */
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "logo_")
    private FileInfo logo;

    /**
     * 医院门头图
     */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "zs_hospital_imgs_")
    private List<FileInfo> imgs;

    /**
     * 医院科室
     */
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "zs_hospital_department_")
    private List<Department> departments;

    @Column(name = "working_time_")
    private String workingTime;

    @Lob
    @Column(name = "departments_name_")
    @Basic(fetch = FetchType.LAZY)
    private String departmentsName;

    @Column(name = "in_id_")
    private String inId;
}
