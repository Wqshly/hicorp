package com.hicorp.segment.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel(value = "com.hicorp.segment.pojo.UserRoleRelation", description = "用户角色关系实体类")
public class UserRoleRelation implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column(name = "user_id", columnDefinition = "bigint", nullable = false)
    @NotNull(message = "用户id不能为空!")
    @Schema(name = "用户id", example = "1")
    private Long userId;

    @Column(name = "role_id", columnDefinition = "bigint", nullable = false)
    @NotNull(message = "角色id不能为空!")
    @Schema(name = "角色id", example = "1")
    private Long roleId;

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

    public UserRoleRelation(Long userId, Long roleId, String createUser, Date createGmt) {
        this.userId = userId;
        this.roleId = roleId;
        this.createUser = createUser;
        this.createGmt = createGmt;
    }
}
