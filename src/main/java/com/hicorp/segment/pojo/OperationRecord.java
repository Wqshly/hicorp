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
 * operation_record
 *
 * @author
 */
@Data
@ApiModel(value = "com.wqs.haier.pojo.RecordOperation", description = "记录用户操作实体类")
public class OperationRecord implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userName;

    private String ip;

    private String requestDetail;

    private String useMethod;

    private String operation;

    private String params;

    private Date gmtCreate;

    private Date gmtModified;

    @Serial
    private static final long serialVersionUID = 1L;

    public OperationRecord(String userName, String ip, String requestDetail, String useMethod, String operation, String params, Date gmtCreate) {
        this.userName = userName;
        this.ip = ip;
        this.requestDetail = requestDetail;
        this.useMethod = useMethod;
        this.operation = operation;
        this.params = params;
        this.gmtCreate = gmtCreate;
    }
}