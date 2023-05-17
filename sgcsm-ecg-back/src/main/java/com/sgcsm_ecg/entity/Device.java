package com.sgcsm_ecg.entity;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author sgcsm
 * @since 2023-05-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "Device", description = "")
@AllArgsConstructor
public class Device implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private String properties;


}
