package com.hicorp.segment.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel(value = "com.hicorp.segment.pojo.ProcessSchemeSub", description = "工艺方案子表实体类")
public class ProcessSchemeSub implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "process_number", columnDefinition = "varchar(45)", length = 45, nullable = false)
    @Schema(name = "工艺方案编码", example = "GY001")
    private String processNumber;

    @Column(name = "scheme_name", columnDefinition = "varchar(45)", length = 45, nullable = false)
    @Schema(name = "物料编码", example = "y10001")
    private String schemeName;

    @Column(name = "process_name", columnDefinition = "varchar(45)", length = 45, nullable = false)
    @Schema(name = "方案名称", example = "钢筋笼加工")
    private String processName;

    @Column(name = "name", columnDefinition = "varchar(45)", length = 45, nullable = false)
    @Schema(name = "工序名称", example = "钢筋断料")
    private String name;

    @Column(name = "pre_process", columnDefinition = "varchar(45)", length = 45)
    @Schema(name = "前置工序", example = "钢筋原材选用")
    private String preProcess;

    @Column(name = "post_process", columnDefinition = "varchar(45)", length = 45)
    @Schema(name = "后置工序", example = "钢筋半成品制作")
    private String postProcess;

    @Column(name = "quality_inspection_scheme", columnDefinition = "varchar(45)")
    @Schema(name = "质检方案", example = "尺寸检查")
    private String qualityInspectionScheme;

    @Column(name = "is_compile_process_plan", columnDefinition = "boolean default false", nullable = false)
    @Schema(name = "是否编制工序计划", example = "false")
    private String isCompileProcessPlan;

    @Column(name = "isPrint", columnDefinition = "boolean default false", nullable = false)
    @Schema(name = "完工后是否打印标签", example = "false")
    private String is_print;

    @Column(name = "plan_report", columnDefinition = "varchar(45)", length = 45, nullable = false)
    @Schema(name = "是否计划和报工", example = "报不合格数量")
    private String planReport;

    @Column(name = "participation_role", columnDefinition = "varchar(45)", length = 45)
    @Schema(name = "角色", example = "钳工")
    private String participationRole;

    @Column(name = "work_description", columnDefinition = "varchar(255)", length = 45)
    @Schema(name = "工作描述", example = "工作需要认真完成")
    private String workDescription;

    @Column(name = "man_hour_quota", columnDefinition = "varchar(45)", length = 45)
    @Schema(name = "工时定额", example = "30工时")
    private String manHourQuota;

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
