package com.hicorp.segment.pojo;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * maintenance_major_hardware
 * @author 
 */
@Data
// @ApiModel是生成接口文档用的。
@ApiModel(value = "com.wqs.haier.pojo.MaintenanceMajorHardware", description = "重大硬件维护实体类")
public class MaintenanceMajorHardware implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String isApprove;
    private String name;

    private String number;

    private String send;

    private String reason;

    private String annex;

    private String engineer;

    private Date dateDayEngineer;

    private String companyRecipientAndTime;

    private String unitRecipientAndTime;

    private String opinion;

    private String result;

    private String leader;

    private Date dateDayLeader;

    private String createUser;

    private Date gmtCreate;

    private String modifiedUser;

    private Date gmtModified;

}