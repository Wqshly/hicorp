package com.hicorp.segment.pojo;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@Data
public class PasswordDO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private String oldPassword;
    private String newPassword;
    private Date gmtModified;
}
