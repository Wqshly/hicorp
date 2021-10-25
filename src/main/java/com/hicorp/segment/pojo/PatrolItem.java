package com.hicorp.segment.pojo;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * patrol_item
 *
 * @author
 */
@Data
// @ApiModel是生成接口文档用的。
@ApiModel(value = "com.wqs.haier.pojo.PatrolItem", description = "巡检项目实体类")
public class PatrolItem implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;



    private String pointNumber;

    private String pointName;

    private String name;

    private String itemType;

    private String remark;

    private String createUser;

    private Date gmtCreate;

    private String modifiedUser;

    private Date gmtModified;
    @Serial
    private static final long serialVersionUID = 1L;
}