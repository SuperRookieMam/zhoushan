package com.sofmit.health.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Entity(name = "zs_department_")
public class Department extends AbstractAuditStateEntity implements Serializable {
    private static final long serialVersionUID = -6191051403698207406L;

    /**
     * 科室名称
     */
    @Column(name = "name_")
    private String name;

    /**
     * 上级Id
     */
    @Column(name = "pid_")
    private Long pid = 0L;

    /**
     * 上级名称
     */
    @Column(name = "pname_")
    private String pname = "无";

    /**
     * 双向绑定医院多对多
     */
    @ManyToMany(mappedBy = "departments")
    private List<Hospital> hospitals;
}
