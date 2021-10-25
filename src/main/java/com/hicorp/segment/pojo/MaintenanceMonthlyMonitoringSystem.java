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
 * maintenance_monthly_monitoring_system
 * @author 
 */
@Data
// @ApiModel是生成接口文档用的。
@ApiModel(value = "com.wqs.haier.pojo.MaintenanceMonthlyMonitoringSystem", description = "月度监控系统维护实体类")
public class MaintenanceMonthlyMonitoringSystem implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String isApprove;
    private String maintenanceUnit;

    private String resettlementSite;

    private String work1Result;

    private String work2Result;

    private String work3Result;

    private String work4Result;

    private String work5Result;

    private String work6Result;

    private String work7Result;

    private String work8Result;

    private String work9Result;

    private String work10Result;

    private String work11Result;

    private String work12Result;

    private String work13Result;

    private String work14Result;

    private String work15Result;

    private Date dateDay;

    private Date arriveTime;

    private Date leaveTime;

    private String informationNote;

    private String engineer;

    private String useUnit;

    private String createUser;

    private Date gmtCreate;

    private String modifiedUser;

    private Date gmtModified;

}