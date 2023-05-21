package com.sgcsm_ecg.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author sgcsm
 * @since 2023-05-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="Log Object", description="")
public class Log implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    @Excel(name = "Record ID")
    private Integer id;

    @Excel(name = "Device ID")
    private String deviceId;

    @Excel(name = "Operator IP")
    private String ip;

    @Excel(name = "Record Creation Time")
    private LocalDateTime createTime;


}
