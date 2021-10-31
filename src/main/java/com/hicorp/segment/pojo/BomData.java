package com.hicorp.segment.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel(value = "com.hicorp.segment.pojo.BomData", description = "BOM主表实体类")
public class BomData implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "number", columnDefinition = "varchar(45)", length = 45, nullable = false)
    @Schema(name = "BOM主键", example = "BOM100001")
    private String number;

    @Column(name = "material_number", columnDefinition = "varchar(45)", length = 45, nullable = false)
    @Schema(name = "物料编码", example = "MT100001")
    private String materialNumber;

    @Column(name = "version", columnDefinition = "varchar(45)", length = 45, nullable = false)
    @Schema(name = "版本", example = "V0.0.1")
    private String version;

    @Column(name = "is_enable", columnDefinition = "boolean default true", nullable = false)
    @Schema(name = "是否启用", example = "true")
    private Boolean isEnable;

    @Column(name = "unit", columnDefinition = "varchar(45)", length = 45, nullable = false)
    @Schema(name = "单位", example = "件")
    private String unit;

    @Column(name = "compile_user", columnDefinition = "varchar(45)", length = 45)
    @Schema(name = "编制", example = "张三")
    private String compileUser;

    @Column(name = "reviewer_user", columnDefinition = "varchar(45)", length = 45)
    @Schema(name = "审核", example = "李四")
    private String reviewerUser;

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
