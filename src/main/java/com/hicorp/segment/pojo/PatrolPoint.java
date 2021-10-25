package com.hicorp.segment.pojo;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@Data
// @ApiModel是生成接口文档用的。
@ApiModel(value = "com.wqs.haier.pojo.PatrolPoint", description = "巡检点实体类")
public class PatrolPoint implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String number;
    private String name;
    private String electronicLabelId;
    private String cardPrintingNumber;
    private String remark;
    private String createUser;
    private String modifiedUser;
    private Date gmtCreate;
    private Date gmtModified;
}
