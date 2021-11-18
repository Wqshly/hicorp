package com.hicorp.segment.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * device_category
 * @author 
 */
@ApiModel(value="com.hicorp.segment.pojo.DeviceCategory")
@Data
public class DeviceCategory implements Serializable {
    private Long id;

    private Object purpose;

    /**
     * 类别名称
     */
    @ApiModelProperty(value="类别名称")
    private String name;

    /**
     * 编号
     */
    @ApiModelProperty(value="编号")
    private String number;

    private String brand;

    private String specificationModel;

    private String unit;

    private String remarks;

    private String createUser;

    private Date createGmt;

    private String modifiedUser;

    private Date modifiedGmt;

    private static final long serialVersionUID = 1L;
}