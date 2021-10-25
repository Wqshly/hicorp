package com.hicorp.segment.pojo;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * login_record
 *
 * @author
 */
@Data
@ApiModel(value = "com.wqs.haier.pojo.LoginRecord", description = "记录登录信息实体类")
public class LoginRecord implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userName;

    private Boolean isLogout;

    private String remoteAddress;

    private Date gmtCreate;

    private Date gmtModified;


    // 登录记录
    public LoginRecord(String userName, Boolean isLogout, String remoteAddress, Date gmtCreate) {
        this.userName = userName;
        this.isLogout = isLogout;
        this.remoteAddress = remoteAddress;
        this.gmtCreate = gmtCreate;
    }

}