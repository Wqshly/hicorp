package com.hicorp.segment.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel(value = "com.hicorp.segment.pojo.BasicCategoryData", description = "基础数据实体类1")
public class BasicCategoryData implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "number", columnDefinition = "varchar(45)", length = 45, nullable = false)
    @Schema(name = "类别编号", example = "GW100001")
    private String number;

    @Column(name = "name", columnDefinition = "varchar(45)", length = 45, nullable = false)
    @Schema(name = "类别名", example = "GW100001")
    private String name;

    @Column(name = "description", columnDefinition = "varchar(255)", nullable = false)
    @Schema(name = "描述", example = "岗位")
    private String description;

    @Column(name = "type", columnDefinition = "varchar(45)", length = 45, nullable = false)
    @Schema(name = "分类", example = "岗位")
    private String type;

    @Column(name = "remark", columnDefinition = "varchar(255)")
    @Schema(name = "备注", example = "岗位")
    private String remark;

    @Column(name = "create_user", columnDefinition = "varchar(45)", length = 45, nullable = false)
    @Schema(name = "创建人", example = "王五")
    private String createUser;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_gmt", columnDefinition = "datetime")
    @Schema(name = "创建时间", example = "2021-11-01 08:00")
    private Date createGmt;

    @Column(name = "modified_user", columnDefinition = "varchar(45)", length = 45)
    @Schema(name = "修改人", example = "张三")
    private String modifiedUser;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "modified_gmt", columnDefinition = "datetime")
    @Schema(name = "修改时间", example = "2021-11-01 08:00")
    private Date modifiedGmt;
}
