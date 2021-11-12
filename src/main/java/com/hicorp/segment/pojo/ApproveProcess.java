package com.hicorp.segment.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * approve_process
 * @author 
 */
@ApiModel(value="com.hicorp.segment.pojo.ApproveProcess")
@Data
public class ApproveProcess implements Serializable {


    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String number;

    /**
     * 审批流名称
     */
    @ApiModelProperty(value="审批流名称")
    private String name;

    /**
     * 审批所对应的表的名字。
     */
    @ApiModelProperty(value="审批所对应的表的名字。")
    private String tableName;

    /**
     * 审批内容
     */
    @ApiModelProperty(value="审批内容")
    private String content;

    private String createUser;

    private Date createGmt;

    private String modifiedUser;

    private Date modifiedGmt;
}