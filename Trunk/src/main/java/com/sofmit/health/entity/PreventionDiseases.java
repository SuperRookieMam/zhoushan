package com.sofmit.health.entity;

import com.dm.file.entity.FileInfo;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * 疾病预防
 */
@Getter
@Setter
@Entity(name = "zs_prevention_diseases_")
public class PreventionDiseases extends AbstractAuditStateEntity implements Serializable {
    private static final long serialVersionUID = -4118377642142856545L;

    /**
     * 内容分类
     */
    @Column(name = "type")
    private String type;

    /**
     * 内容标题
     */
    @Column(name = "title_")
    private String title;

    /**
     * 作者来源
     */
    @Column(name = "source_")
    private String source;
    /**
     * 封面
     */
    @ManyToOne
    @JoinColumn(name = "cover_img_")
    private FileInfo coverImg;
    /**
     * 内容
     */
    @Lob
    @Column(name = "content_")
    @Basic(fetch = FetchType.LAZY)
    private String content;

    @Column(name = "read_number")
    private Integer readNumber = 0;
    /**
     * 附件
     */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "zs_prevention_diseases_accessory_")
    private List<FileInfo> accessory;
}
