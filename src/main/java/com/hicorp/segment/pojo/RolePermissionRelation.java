package com.hicorp.segment.pojo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * @Author:
 * @Date: Created in 12:50 2021/6/1
 * @Description:
 * @ChineseDescription:
 * @Modified_By:
 */
@Data
@Table(name = "role_permission_relation")
@Entity
@Schema(name = "com.hicorp.segment.pojo.RolePermissionRelation", description = "角色权限关系实体类")
public class RolePermissionRelation implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column(name = "role_id")
    private Long roleId;

    @Column(name = "permission_id")
    private Long permissionId;

    @Column(name = "create_user", columnDefinition = "varchar(45)", length = 45, nullable = false)
    @Schema(name = "创建人", example = "王五")
    private String createUser;

    @Temporal(TemporalType.TIMESTAMP)
    @NotNull(message = "创建时间不能为空!")
    @Column(name = "create_gmt", columnDefinition = "datetime", nullable = false)
    @Schema(name = "创建时间", example = "2021-11-01 08:00")
    private Date createGmt;

    public RolePermissionRelation() {
    }

    public RolePermissionRelation(Long roleId, Long permissionId, String createUser, Date createGmt) {
        this.roleId = roleId;
        this.permissionId = permissionId;
        this.createUser = createUser;
        this.createGmt = createGmt;
    }
}
