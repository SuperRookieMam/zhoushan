package com.sofmit.health.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * 疾病
 */
@Getter
@Setter
@Entity(name = "zs_illness_")
public class Illness extends AbstractAuditStateEntity implements Serializable {
    private static final long serialVersionUID = 4344963670531409691L;

    /**
     * 疾病名称
     */
    @Column(name = "name_")
    private String name;

    /**
     * 身体部位
     */
    @Column(name = "body_part_")
    private String bodyPart;

    /**
     * 人群
     */
    @Column(name = "crowd_")
    private String crowd;

    /**
     * 年龄段
     */
    @Column(name = "ag_group_")
    private String ageGroup;

    /**
     * 就诊科室
     */
    @Column(name = "clinic_department_")
    private String clinicDepartment;

    /**
     * 科室
     */
    @ManyToMany
    @JoinTable(name = "zs_illness_zs_department_")
    private List<Department> departments;

}
