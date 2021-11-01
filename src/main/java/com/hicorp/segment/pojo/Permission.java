package com.hicorp.segment.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
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
@ApiModel(value = "com.hicorp.segment.pojo.Permission", description = "权限实体类")
public class Permission implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tag", columnDefinition = "varchar(45)")
    @Schema(name = "所属类别", example = "审核")
    private String tag;

    @Column(name = "method", columnDefinition = "char(8)")
    @Schema(name = "请求方式", example = "post")
    private String method;

    @Column(name = "api_path", columnDefinition = "varchar(255)")
    @Schema(name = "暴露的api的路径", example = "/bomMain/list")
    private String apiPath;

    @Column(name = "operation_id", columnDefinition = "varchar(255)")
    @Schema(name = "操作的唯一标识符", example = "getBom GET 1")
    private String operationId;

    @Column(name = "summary", columnDefinition = "varchar(255)")
    @Schema(name = "操作内容", example = "获取角色菜单")
    private String summary;

    @Column(name = "description", columnDefinition = "varchar(255)")
    @Schema(name = "具体描述", example = "管理所有用户")
    private String description;

    @Temporal(TemporalType.TIMESTAMP)
    @NotNull(message = "创建时间不能为空!")
    @Column(name = "create_gmt", columnDefinition = "datetime", nullable = false)
    @Schema(name = "创建时间", example = "2021-11-01 08:00")
    private Date createGmt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "modified_gmt", columnDefinition = "datetime")
    @Schema(name = "修改时间", example = "2021-11-01 08:00")
    private Date modifiedGmt;

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