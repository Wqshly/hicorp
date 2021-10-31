package com.hicorp.segment.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * login_record
 *
 * @author
 */
@Data
@ApiModel(value = "com.hicorp.segment.pojo.LoginRecord", description = "记录登录信息的实体类")
public class LoginRecord implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_name", columnDefinition = "varchar(45)", length = 45, nullable = false)
    @Schema(name = "登录用户名", example = "user1")
    private String userName;

    @Column(name = "is_logout", columnDefinition = "boolean default false")
    @Schema(name = "登录还是登出，true表示登入", example = "false")
    private Boolean isLogout;

    @Column(name = "remote_address", columnDefinition = "boolean default false")
    @Schema(name = "登录者地址", example = "127.0.0.1")
    private String remoteAddress;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_gmt", columnDefinition = "datetime")
    @Schema(name = "创建时间", example = "2021-11-01 08:00")
    private Date createGmt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "modified_gmt", columnDefinition = "datetime")
    @Schema(name = "修改时间", example = "2021-11-01 08:00")
    private Date modifiedGmt;

    public LoginRecord(String userName, Boolean isLogout, String remoteAddress, Date createGmt) {
        this.userName = userName;
        this.isLogout = isLogout;
        this.remoteAddress = remoteAddress;
        this.createGmt = createGmt;
    }

}