package com.hicorp.segment.pojo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.persistence.*;
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
@Table(name = "role_menu_relation")
@Entity
@Schema(name = "com.wqs.haier.pojo.UserRoleRelation", description = "UserRole关系实体类")
public class RoleMenuRelation implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column(name = "role_id")
    private Long roleId;

    @Column(name = "menu_id")
    private Long menuId;

    @Column(name = "create_user")
    private String createUser;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "gmt_create", columnDefinition = "datetime", nullable = false)
    private Date gmtCreate;

    @Column(name = "modified_user")
    private String modifiedUser;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "gmt_modified", columnDefinition = "datetime")
    private Date gmtModified;

    @Serial
    private static final long serialVersionUID = 1L;

    public RoleMenuRelation(Long roleId, Long menuId, String createUser, Date gmtCreate) {
        this.roleId = roleId;
        this.menuId = menuId;
        this.createUser = createUser;
        this.gmtCreate = gmtCreate;
    }
}
