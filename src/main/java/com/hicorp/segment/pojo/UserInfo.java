package com.hicorp.segment.pojo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * user
 *
 * @author
 */
@Data
@Table(name = "user_info")
@Entity
@Schema(name = "com.wqs.haier.pojo.UserInfo", description = "用户(员工)信息实体类")
public class UserInfo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Transient
    private Long userId;

    @Column(name = "name", columnDefinition = "varchar(45)", length = 45, nullable = false)
    @NotNull(message = "该值不能为空!")
    @NotEmpty(message = "该值不能为空字符串!")
    @Length(min = 1, max = 45)
    private String name;

    @Column(name = "staff_number", columnDefinition = "varchar(45)", length = 45, nullable = false)
    @NotNull(message = "该值不能为空!")
    @NotEmpty(message = "该值不能为空字符串!")
    @Length(min = 1, max = 45)
    private String staffNumber;

    @Column(name = "phone_number", columnDefinition = "varchar(16)", length = 16)
    @NotNull(message = "该值不能为空!")
    @NotEmpty(message = "该值不能为空字符串!")
    @Length(min = 11, max = 13, message = "号码长度有误，请核对!")
    private String phoneNumber;

    @Column(name = "id_card", columnDefinition = "varchar(18)", length = 18)
    @NotNull(message = "该值不能为空!")
    @NotEmpty(message = "该值不能为空字符串!")
    @Length(min = 18, max = 18, message = "身份证号长度有误，应为18位，请核对!")
    private String idCard;

    @Column(name = "sex", columnDefinition = "tinyint(4)")
    private Boolean sex;

    private String department;

    private String createUser;

    private Date gmtCreate;

    private String modifiedUser;

    private Date gmtModified;

    @Serial
    private static final long serialVersionUID = 1L;
}