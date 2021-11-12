package com.hicorp.segment.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * approve_process_sub
 * @author 
 */
@Table(name="approve_process_sub")
@ApiModel(value="com.hicorp.segment.pojo.ApproveProcessSub")
@Data
public class ApproveProcessSub implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "JDBC")
    private Long id;

    private Long approveProcessId;

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

    /**
     * 审批级别
     */
    @ApiModelProperty(value="审批级别")
    private Integer level;

    /**
     * 审批人
     */
    @ApiModelProperty(value="审批人")
    private String approver;

    /**
     * 前一级的审批人
     */
    @ApiModelProperty(value="前一级的审批人")
    private String preApprover;

    /**
     * 后一级的审批人
     */
    @ApiModelProperty(value="后一级的审批人")
    private String postApprover;

    private String createUser;

    private Date createGmt;

    private String modifiedUser;

    private Date modifiedGmt;
}