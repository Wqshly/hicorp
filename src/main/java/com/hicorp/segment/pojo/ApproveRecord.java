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
 * approve_record
 * @author 
 */
@ApiModel(value="com.hicorp.segment.pojo.ApproveRecord审批记录表")
@Data
public class ApproveRecord implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "JDBC")
    private Long id;

    /**
     * 审批的表的名称
     */
    @ApiModelProperty(value="审批的表的名称")
    private String approverTableName;

    /**
     * 具体审核的是哪一条单据(数据)
     */
    @ApiModelProperty(value="具体审核的是哪一条单据(数据)")
    private Long detailColumnId;

    private String currentApprover;

    private String preApprover;

    private String postApprover;

    private Byte isEnd;

    private Byte isApproved;

    /**
     * 审批意见
     */
    @ApiModelProperty(value="审批意见")
    private String approvalComments;

    /**
     * 审批状态, 例如: 已通过、已退回、已超时等。
     */
    @ApiModelProperty(value="审批状态, 例如: 已通过、已退回、已超时等。")
    private String status;

    private String createUser;

    private Date createGmt;

    private String modifiedUser;

    private Date modifiedGmt;
}