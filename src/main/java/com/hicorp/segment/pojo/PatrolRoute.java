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
 * patrol_route
 * @author 
 */
@Data
// @ApiModel是生成接口文档用的。
@ApiModel(value = "com.wqs.haier.pojo.PatrolRoute", description = "巡检路线实体类")
public class PatrolRoute implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String cycleNumber;

    private String cycleName;

    private String cycleOrder;

    private String pointName;

    private String remark;

    private String createUser;

    private Date gmtCreate;

    private String modifiedUser;

    private Date gmtModified;
}