package com.hicorp.segment.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * external_staff
 * @author 
 */
@ApiModel(value="com.hicorp.segment.pojo.ExternalStaff")
@Data
public class ExternalStaff implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userAccount;

    private String contactTeamNumber;

    /**
     * 所属承包队队名
     */
    @ApiModelProperty(value="所属承包队队名")
    private String contactTeamName;

    /**
     * 工号
     */
    @ApiModelProperty(value="工号")
    private String number;

    /**
     * 姓名
     */
    @ApiModelProperty(value="姓名")
    private String name;

    private String phone;

    /**
     * 性别
     */
    @ApiModelProperty(value="性别")
    private Byte sex;

    /**
     * 身份证号
     */
    @ApiModelProperty(value="身份证号")
    private String identify;

    /**
     * 是否注册用户(默认为否)
     */
    @ApiModelProperty(value="是否注册用户(默认为否)")
    private Byte isUser;

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
}