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
 * maintenance_daily_monitoring_system_room
 * @author 
 */
@Data
// @ApiModel是生成接口文档用的。
@ApiModel(value = "com.wqs.haier.pojo.MaintenanceDailyMonitoringSystemRoom", description = "监控系统机房日常维护实体类")
public class MaintenanceDailyMonitoringSystemRoom implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String isApprove;

    private String name;

    private String number;

    private String maintenanceUnit;

    private String maintenanceLocation;

    private String electricResult;

    private String electricConclusion;

    private String temperatureResult;

    private String temperatureConclusion;

    private String humidityResult;

    private String humidityConclusion;

    private String dirtResult;

    private String dustProofConclusion;

    private String storageResult;

    private String storageConclusion;

    private String imageResult;

    private String imageConclusion;

    private String controlResult;

    private String controlConclusion;

    private String deviceAttachmentResult;

    private String deviceAttachmentConclusion;

    private String abnormalReason;

    private String debuggingResult;

    private String significantProblem;

    private String engineer;

    private Date dateDayEngineer;

    private String userDepartmentOpinion;

    private String chargePerson;

    private Date dateDayCharge;

    private String createUser;

    private Date gmtCreate;

    private String modifiedUser;

    private Date gmtModified;

}