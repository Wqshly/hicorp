package com.hicorp.segment.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * bom_main
 * @author 
 */
@ApiModel(value="com.hicorp.segment.pojo.BomMain")
@Data
public class BomMain implements Serializable {
    private Long id;

    private String bomNo;

    private String fullName;

    private String name;

    private String createUser;

    private Date createGmt;

    private String modifyUser;

    private Date modifyGmt;

    private String checkUser;

    private Date checkGmt;

    private String invalidUser;

    private Date invalidGmt;

    private Boolean isInvalid;

    private static final long serialVersionUID = 1L;
}