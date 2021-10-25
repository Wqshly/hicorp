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
 * patrol_task
 * @author 
 */
@Data
// @ApiModel是生成接口文档用的。
@ApiModel(value = "com.wqs.haier.pojo.PatrolTask", description = "巡检任务实体类")
public class PatrolTask implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String taskName;

    private String time;

    private String useTime;

    private String employee;

    private String taskContent;

    private String remark;

    private String createUser;

    private Date gmtCreate;

    private String modifiedUser;

    private Date gmtModified;

}