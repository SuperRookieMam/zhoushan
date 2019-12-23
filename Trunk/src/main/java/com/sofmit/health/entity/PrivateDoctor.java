package com.sofmit.health.entity;

import com.dm.file.entity.FileInfo;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * 私人小医生
 */
@Getter
@Setter
@Entity(name = "zs_private_doctor_")
public class PrivateDoctor extends AbstractAuditStateEntity implements Serializable {
    private static final long serialVersionUID = -3941827666898038759L;

    /**
     * 编号
     */
    @Column(name = "number_")
    private String number;

    /**
     * 名称
     */
    @Column(name = "name_")
    private String name;

    /**
     * 别名
     */
    @Column(name = "another_name_")
    private String anotherName;

    /**
     * 就诊科室
     */
    @ManyToOne
    @JoinColumn(name = "department_")
    private Department department;

    /**
     * 概述
     */
    @Lob
    @Column(name = "summarize_")
    @Basic(fetch = FetchType.LAZY)
    private String summarize;

    /**
     * 症状
     */
    @Lob
    @Column(name = "symptom_")
    @Basic(fetch = FetchType.LAZY)
    private String symptom;

    /**
     * 病因
     */
    @Lob
    @Column(name = "pathogen_")
    @Basic(fetch = FetchType.LAZY)
    private String pathogen;

    /**
     * 就医
     */
    @Lob
    @Column(name = "medical_advice_")
    @Basic(fetch = FetchType.LAZY)
    private String medicalAdvice;

    /**
     * 治疗
     */
    @Lob
    @Column(name = "treat_")
    @Basic(fetch = FetchType.LAZY)
    private String treat;

    /**
     * 治疗
     */
    @Lob
    @Column(name = "prognosis_")
    @Basic(fetch = FetchType.LAZY)
    private String prognosis;

    /**
     * 日常
     */
    @Lob
    @Column(name = "general_")
    @Basic(fetch = FetchType.LAZY)
    private String general;

    /**
     * 图片
     */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "zs_private_doctor_imgs_")
    private List<FileInfo> imgs;

    /**
     * 发布状态
     */
    @Column(name = "releaseState_")
    private Integer releaseState = 0;

    @Lob
    @Column(name = "departments_name_")
    @Basic(fetch = FetchType.LAZY)
    private String departmentsName;
}
