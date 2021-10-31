package com.hicorp.segment.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel(value = "com.hicorp.segment.pojo.Staff", description = "员工的实体类")
public class Staff implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    @Schema(name = "用户ID", example = "1")
    private Long id;

    @Column(name = "name", columnDefinition = "varchar(45)", length = 45, nullable = false)
    @Schema(name = "姓名", example = "张三")
    private String name;

    @Column(name = "number", columnDefinition = "varchar(45)", nullable = false)
    @Schema(name = "员工编号", example = "yg100001")
    private String number;

    @Column(name = "sex", columnDefinition = "boolean default false", nullable = false)
    @Schema(name = "性别, false为女, true为男", example = "false")
    private String sex;

    @Column(name = "identify", columnDefinition = "varchar(18)", nullable = false)
    @Schema(name = "身份证号", example = "370101199101012345")
    private String identify;

    @Column(name = "is_user", columnDefinition = "boolean default false", nullable = false)
    @Schema(name = "是否为用户", example = "false")
    private String isUser;

    @Column(name = "user_account", columnDefinition = "varchar(45)")
    @Schema(name = "关联账号", example = "HQ100001")
    private String userAccount;

    @Column(name = "create_user", columnDefinition = "varchar(45)", length = 45, nullable = false)
    @Schema(name = "创建人", example = "王五")
    private String createUser;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_gmt", columnDefinition = "datetime", nullable = false)
    @Schema(name = "创建时间", example = "2021-11-01 08:00")
    private Date createGmt;

    @Column(name = "modified_user", columnDefinition = "varchar(45)")
    @Schema(name = "修改人", example = "张三")
    private String modifiedUser;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "modified_gmt", columnDefinition = "datetime")
    @Schema(name = "修改时间", example = "2021-11-01 08:00")
    private Date modifiedGmt;
}
