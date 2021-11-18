package com.hicorp.segment.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * customer
 * @author 
 */
@ApiModel(value="com.hicorp.segment.pojo.Customer客户表")
@Data
public class Customer implements Serializable {
    /**
     * 主键
     */
    @ApiModelProperty(value="主键")
    private Long id;

    /**
     * 客户编码
     */
    @ApiModelProperty(value="客户编码")
    private String number;

    /**
     * 客户姓名
     */
    @ApiModelProperty(value="客户姓名")
    private String name;

    /**
     * 全称
     */
    @ApiModelProperty(value="全称")
    private String fullName;

    /**
     * 联系人
     */
    @ApiModelProperty(value="联系人")
    private String contactPerson;

    /**
     * 邮箱
     */
    @ApiModelProperty(value="邮箱")
    private String email;

    /**
     * 地址
     */
    @ApiModelProperty(value="地址")
    private String address;

    /**
     * 手机号
     */
    @ApiModelProperty(value="手机号")
    private String phoneNumber;

    private String remark;

    /**
     * 创建人
     */
    @ApiModelProperty(value="创建人")
    private String createUser;

    /**
     * 创建时间
     */
    @ApiModelProperty(value="创建时间")
    private Date createGmt;

    /**
     * 修改人
     */
    @ApiModelProperty(value="修改人")
    private String modifiedUser;

    /**
     * 修改时间
     */
    @ApiModelProperty(value="修改时间")
    private Date modifiedGmt;

    private static final long serialVersionUID = 1L;
}