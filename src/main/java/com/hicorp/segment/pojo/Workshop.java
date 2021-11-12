package com.hicorp.segment.pojo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@Data
public class Workshop implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;


    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", columnDefinition = "varchar(55)", length = 55, nullable = false)
    @Schema(name = "名称", example = "第一车间")
    private String name;

    @Column(name = "number", columnDefinition = "varchar(45)", length = 45, nullable = false)
    @Schema(name = "编号", example = "WS100001")
    private String number;

    @Column(name = "factory_name", columnDefinition = "varchar(45)", length = 45, nullable = false)
    @Schema(name = "工厂名", example = "青岛一厂")
    private String factoryName;

    @Column(name = "factory_number", columnDefinition = "varchar(45)", length = 45, nullable = false)
    @Schema(name = "工厂编号", example = "GW100001")
    private String factoryNumber;

    @Column(name = "charge_person", columnDefinition = "varchar(45)", length = 45, nullable = false)
    @Schema(name = "负责人", example = "李四")
    private String chargePerson;

    @Column(name = "create_user", columnDefinition = "varchar(45)", length = 45, nullable = false)
    @Schema(name = "创建人", example = "王五")
    private String createUser;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_gmt", columnDefinition = "datetime")
    @Schema(name = "创建时间", example = "2021-11-01 08:00")
    private Date createGmt;

    @Column(name = "modified_user", columnDefinition = "varchar(45)", length = 45)
    @Schema(name = "修改人", example = "张三")
    private String modifiedUser;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "modified_gmt", columnDefinition = "datetime")
    @Schema(name = "修改时间", example = "2021-11-01 08:00")
    private Date modifiedGmt;

}
