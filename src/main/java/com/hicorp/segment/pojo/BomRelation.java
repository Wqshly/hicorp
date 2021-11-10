package com.hicorp.segment.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel(value = "com.hicorp.segment.pojo.MaterialRelation", description = "物料关系实体类(BOM子表)")
public class BomRelation implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Column(name = "bom_number", columnDefinition = "varchar(45)", length = 45, nullable = false)
    @Schema(name = "物料编码", example = "z100001")
    private String bomNumber;

    @Column(name = "parent_id", columnDefinition = "bigint")
    @Schema(name = "父类id", example = "1")
    private String parentId;

    @Column(name = "parent_material_number", columnDefinition = "varchar(45)", length = 45)
    @Schema(name = "父类物料编码", example = "z100001")
    private String parentMaterialNumber;

    @Column(name = "material_number", columnDefinition = "varchar(45)", length = 45, nullable = false)
    @Schema(name = "物料编码", example = "z100002")
    private String materialNumber;

    @Column(name = "material_name", columnDefinition = "varchar(45)", length = 45, nullable = false)
    @Schema(name = "物料名称", example = "预制管片环")
    private String materialName;

    @Column(name = "quantity", columnDefinition = "int", nullable = false)
    @Schema(name = "数量", example = "50")
    private String quantity;

    @Column(name = "unit", columnDefinition = "varchar(45)", nullable = false)
    @Schema(name = "单位", example = "件")
    private String unit;

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
