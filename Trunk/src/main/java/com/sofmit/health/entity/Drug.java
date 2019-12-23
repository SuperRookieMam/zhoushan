package com.sofmit.health.entity;

import com.dm.file.entity.FileInfo;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * 药品
 */
@Getter
@Setter
@Entity(name = "zs_drug_")
public class Drug extends AbstractAuditStateEntity implements Serializable {
    private static final long serialVersionUID = 3769253373999116039L;

    /**
     * 药品编号
     */
    @Column(name = "number_")
    private String number;
    /**
     * 药品名称
     */
    @Column(name = "name_")
    private String name;

    /**
     * 厂商
     */
    @Column(name = "factory_")
    private String factory;

    /**
     * 规格
     */
    @Column(name = "standard_")
    private String standard;

    /**
     * 剂型
     */
    @Column(name = "dosage_form_")
    private String dosageForm;

    /**
     * 参考价格
     */
    @Column(name = "price_")
    private Double price;

    /**
     * 自负比列
     */
    @Column(name = "self_conceit_")
    private String selfConceit;

    /**
     * 在职自负比例
     */
    @Column(name = "in_job_self_conceit_")
    private Double inJobSelfConceit;

    /**
     * 离职自负比例
     */
    @Column(name = "dimission_self_conceit_")
    private Double dimissionSelfConceit;

    /**
     * 退休
     */
    @Column(name = "retire_self_conceit_")
    private Double retireSelfConceit;

    /**
     * 限定支付
     */
    @Column(name = "qualified_to_pay_")
    private String qualifiedToPay;
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
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "zs_drug_imgs_")
    private List<FileInfo> imgs;

    @Column(name = "in_id_")
    private String inId;
}
