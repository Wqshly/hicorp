package com.hicorp.segment.pojo;

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
@Schema(name = "com.wqs.haier.pojo.Role", description = "角色实体类")
public class Role implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column(name = "name", columnDefinition = "varchar(45)", length = 45, nullable = false)
    @NotEmpty(message = "姓名不能为空!")
    private String name;

    @Column(name = "introduction", columnDefinition = "varchar(45)", length = 45, nullable = false)
    @NotEmpty(message = "具体描述不能为空!")
    private String introduction;

    @Column(name = "create_user", columnDefinition = "varchar(45)", length = 45, nullable = false)
    private String createUser;

    @Temporal(TemporalType.TIMESTAMP)
    @NotNull(message = "创建时间不能为空!")
    @Column(name = "gmt_create", columnDefinition = "datetime", nullable = false)
    private Date gmtCreate;

    @Column(name = "modified_user", columnDefinition = "varchar(45)", length = 45)
    private String modifiedUser;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "gmt_modified", columnDefinition = "datetime")
    private Date gmtModified;

    @Serial
    private static final long serialVersionUID = 1L;
}