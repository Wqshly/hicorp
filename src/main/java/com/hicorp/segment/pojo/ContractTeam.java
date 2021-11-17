package com.hicorp.segment.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * contract_team
 * @author 
 */
@ApiModel(value="com.hicorp.segment.pojo.ContractTeam承包队")
@Data
public class ContractTeam implements Serializable {
    private Long id;

    private String number;

    /**
     * 承包队名
     */
    @ApiModelProperty(value="承包队名")
    private String name;

    /**
     * 负责人
     */
    @ApiModelProperty(value="负责人")
    private String chargePerson;

    private String phoneNumber;

    private String createUser;

    private Date createGmt;

    private String modifiedUser;

    private Date modifiedGmt;

    private static final long serialVersionUID = 1L;
}