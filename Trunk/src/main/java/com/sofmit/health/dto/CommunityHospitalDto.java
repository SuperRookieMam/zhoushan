package com.sofmit.health.dto;

import com.dm.file.dto.FileInfoDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

/**
 * 社区医院数据模型
 *
 * @author 李东
 * @since
 * @Copyright (C) 2019 江苏云智网络科技股份有限公司版权所有
 *
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CommunityHospitalDto extends AbstractDto<Long> implements Serializable {

    private static final long serialVersionUID = 1L;
    private String type;
    private String name;
    private String tel;
    private Double longitude;
    private Double latitude;
    private String address;
    private List<FileInfoDto> pics;
    private FileInfoDto logo;
    private String serviceTime;
    private List<VaccineDto> vaccines;
    private String vaccinesName;
    private String detail;
    private String inId;
}
