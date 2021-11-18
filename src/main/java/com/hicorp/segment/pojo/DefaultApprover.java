package com.hicorp.segment.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;

/**
 * approve_process_sub
 * @author 
 */
@Table(name="default_approver")
@ApiModel(value="com.hicorp.segment.pojo.DefaultApprover")
@Data
public class DefaultApprover implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "JDBC")
    private Long id;

    private Long approveProcessId;

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

}