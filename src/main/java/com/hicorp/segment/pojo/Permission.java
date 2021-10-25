package com.hicorp.segment.pojo;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * permission
 *
 * @author
 */
@Data
@Table(name = "permission")
@Entity
@ApiModel(value = "com.wqs.haier.pojo.Permission", description = "权限实体类")
public class Permission implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tag;

    private String method;

    private String apiPath;

    private String operationId;

    private String summary;

    private String description;

    private Date gmtCreate;

    private Date gmtModified;

    @Serial
    private static final long serialVersionUID = 1L;

    public Permission() {
    }

    public Permission(String apiPath, String summary) {
        this.apiPath = apiPath;
        this.summary = summary;
    }

    public Permission(String tag, String method, String apiPath, String operationId, String summary, String description) {
        this.tag = tag;
        this.method = method;
        this.apiPath = apiPath;
        this.operationId = operationId;
        this.summary = summary;
        this.description = description;
    }
}