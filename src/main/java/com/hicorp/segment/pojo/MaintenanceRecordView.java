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
 * maintenance_record_view
 * @author 
 */
@Data
// @ApiModel是生成接口文档用的。
@ApiModel(value = "com.wqs.haier.pojo.MaintenanceRecordView", description = "维护记录查看实体类")
public class MaintenanceRecordView implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long classificationId;

    private Date dateDay;

    private String number;

    private String remark;

    private String createUser;

    private Date gmtCreate;

    private String modifiedUser;

    private Date gmtModified;

}