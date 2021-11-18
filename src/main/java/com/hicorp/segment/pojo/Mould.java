package com.hicorp.segment.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * mould
 * @author 
 */
@ApiModel(value="com.hicorp.segment.pojo.Mould")
@Data
public class Mould implements Serializable {
    private Long id;

    /**
     * 基础编码
     */
    @ApiModelProperty(value="基础编码")
    private String baseNumber;

    /**
     * 模具编码
     */
    @ApiModelProperty(value="模具编码")
    private String number;

    /**
     * 模具类别编号
     */
    @ApiModelProperty(value="模具类别编号")
    private String typeNumber;

    /**
     * 模具类别名称
     */
    @ApiModelProperty(value="模具类别名称")
    private String typeName;

    /**
     * RFID标签识别号
     */
    @ApiModelProperty(value="RFID标签识别号")
    private String rfidNumber;

    /**
     * 生产厂家
     */
    @ApiModelProperty(value="生产厂家")
    private String manufacturer;

    /**
     * 规格、尺寸
     */
    @ApiModelProperty(value="规格、尺寸")
    private String specificationSize;

    /**
     * 单位(规格)
     */
    @ApiModelProperty(value="单位(规格)")
    private String unit;

    /**
     * 累计使用次数
     */
    @ApiModelProperty(value="累计使用次数")
    private Integer cumulativeUsageTimes;

    /**
     * 入厂时间
     */
    @ApiModelProperty(value="入厂时间")
    private Date incomingTime;

    private static final long serialVersionUID = 1L;
}