package com.hicorp.segment.pojo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * department
 *
 * @author
 */
@Data
@Entity
@Schema(name = "com.wqs.haier.pojo.Department", description = "部门实体类")
public class Department implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String number;

    private String name;

    private String createUser;

    private Date gmtCreate;

    private String modifiedUser;

    private Date gmtModified;
}