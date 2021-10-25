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
@Table(name = "user_role_relation")
@Entity
@Schema(name = "com.wqs.haier.pojo.UserRoleRelation", description = "UserRole关系实体类")
public class UserRoleRelation implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "role_id")
    private Long roleId;

    @Column(name = "create_user")
    private String createUser;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "gmt_create", columnDefinition = "datetime", nullable = false)
    private Date gmtCreate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "gmt_modified", columnDefinition = "datetime")
    private Date gmtModified;

    @Serial
    private static final long serialVersionUID = 1L;

    public UserRoleRelation() {
    }

    public UserRoleRelation(Long userId, Long roleId, String createUser, Date gmtCreate) {
        this.userId = userId;
        this.roleId = roleId;
        this.createUser = createUser;
        this.gmtCreate = gmtCreate;
    }
}
