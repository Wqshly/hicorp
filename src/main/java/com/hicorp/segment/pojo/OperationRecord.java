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
 * operation_record
 *
 * @author
 */
@Data
@ApiModel(value = "com.hicorp.segment.pojo.OperationRecord", description = "记录用户操作实体类")
public class OperationRecord implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_name", columnDefinition = "varchar(45)", length = 45, nullable = false)
    @Schema(name = "用户名", example = "张三")
    private String userName;

    @Column(name = "ip", columnDefinition = "varchar(20)", length = 20, nullable = false)
    @Schema(name = "用户ip", example = "192.0.0.1")
    private String ip;

    @Column(name = "operation", columnDefinition = "varchar(45)", length = 45, nullable = false)
    @Schema(name = "操作", example = "删除用户")
    private String operation;

    @Column(name = "request_detail", columnDefinition = "varchar(255)", nullable = false)
    @Schema(name = "请求详情", example = "192.0.0.1")
    private String requestDetail;

    @Column(name = "use_method", columnDefinition = "varchar(255)", nullable = false)
    @Schema(name = "请求方式", example = "post")
    private String useMethod;

    @Column(name = "params", columnDefinition = "text", nullable = false)
    @Schema(name = "参数", example = "name = 100, page = 2, sort = id")
    private String params;

    @Temporal(TemporalType.TIMESTAMP)
    @NotNull(message = "创建时间不能为空!")
    @Column(name = "create_gmt", columnDefinition = "datetime", nullable = false)
    @Schema(name = "创建时间", example = "2021-11-01 08:00")
    private Date createGmt;

    @Serial
    private static final long serialVersionUID = 1L;

    public OperationRecord(String userName, String ip, String requestDetail, String useMethod, String operation, String params, Date createGmt) {
        this.userName = userName;
        this.ip = ip;
        this.requestDetail = requestDetail;
        this.useMethod = useMethod;
        this.operation = operation;
        this.params = params;
        this.createGmt = createGmt;
    }
}