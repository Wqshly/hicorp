package com.hicorp.segment.pojo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * user
 *
 * @author
 */
@Data
@Entity
@Schema(name = "com.wqs.haier.pojo.User", description = "用户实体类")
public class User implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    @Schema(name = "用户ID", example = "1")
    private Long id;

    @Column(name = "user_name", columnDefinition = "varchar(45)", length = 45, nullable = false)
    @Schema(name = "外键，用户名", example = "root")
    private String userName;

    @Column(name = "password", columnDefinition = "varchar(255)", nullable = false)
    @Schema(name = "用户密码", example = "wqs123456")
    private String password;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "gmt_create", columnDefinition = "datetime", nullable = false)
    private Date gmtCreate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "gmt_modified", columnDefinition = "datetime")
    private Date gmtModified;
}