package com.hicorp.segment.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * device
 * @author 
 */
@ApiModel(value="com.hicorp.segment.pojo.Device")
@Data
public class Device implements Serializable {
    private Long id;

    /**
     * 设备编号
     */
    @ApiModelProperty(value="设备编号")
    private String number;

    /**
     * 设备名
     */
    @ApiModelProperty(value="设备名")
    private String name;

    /**
     * 存放点位
     */
    @ApiModelProperty(value="存放点位")
    private String address;

    /**
     * 状态(维修、使用中、年检中、报废等)
     */
    @ApiModelProperty(value="状态(维修、使用中、年检中、报废等)")
    private String status;

    /**
     * 何时开始使用？
     */
    @ApiModelProperty(value="何时开始使用？")
    private Date useBegin;

    private String createUser;

    private Date createGmt;

    private String modifiedUser;

    private Date modifiedGmt;

    private static final long serialVersionUID = 1L;
}