package com.hicorp.segment.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * role
 *
 * @author
 */
@Data
@Table(name = "role")
@Entity
@ApiModel(value = "com.hicorp.segment.pojo.Role", description = "角色实体类")
public class Role implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column(name = "name", columnDefinition = "varchar(45)", length = 45, nullable = false)
    @Schema(name = "角色名", example = "超级管理员")
    @NotEmpty(message = "角色名称不能为空!")
    private String name;

    @Column(name = "introduction", columnDefinition = "varchar(45)", length = 45, nullable = false)
    @Schema(name = "角色描述", example = "管理所有用户")
    @NotEmpty(message = "具体描述不能为空!")
    private String introduction;

    @Column(name = "create_user", columnDefinition = "varchar(45)", length = 45, nullable = false)
    @Schema(name = "创建人", example = "王五")
    private String createUser;

    @Temporal(TemporalType.TIMESTAMP)
    @NotNull(message = "创建时间不能为空!")
    @Column(name = "create_gmt", columnDefinition = "datetime", nullable = false)
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