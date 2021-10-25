package com.hicorp.segment.pojo;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class PasswordFindDO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private String staffNumber;
    private String name;
    private String phoneNumber;
    private String idCard;
    private String newPassword;
    private String confirmPwd;
}
