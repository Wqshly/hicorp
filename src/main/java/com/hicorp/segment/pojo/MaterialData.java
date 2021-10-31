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
@ApiModel(value = "com.hicorp.segment.pojo.MaterialData", description = "物料实体类")
public class MaterialData implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Column(name = "number", columnDefinition = "varchar(45)", length = 45, nullable = false)
    @Schema(name = "物料编码", example = "z100001")
    private String number;

    @Column(name = "name", columnDefinition = "varchar(45)", length = 45, nullable = false)
    @Schema(name = "物料名称", example = "预制管片环")
    private String name;

    @Column(name = "type", columnDefinition = "varchar(45)", length = 45, nullable = false)
    @Schema(name = "类别", example = "自制件")
    private String type;

    @Column(name = "spec", columnDefinition = "varchar(45)", length = 45, nullable = false)
    @Schema(name = "规格", example = "直径6米")
    private String spec;

    @Column(name = "safety_stock", columnDefinition = "varchar(45)", length = 45, nullable = false)
    @Schema(name = "安全库存", example = "50")
    private String safetyStock;

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
