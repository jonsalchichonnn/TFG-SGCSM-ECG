package com.sgcsm_ecg.entity;

import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author sgcsm
 * @since 2023-05-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="Menu对象", description="")
public class Menu implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    private String menuCode;

    private String menuName;

    private String menuLevel;

    private String menuParentCode;

    private String menuPath;

    @ApiModelProperty(value = "Permission 0=superAdmin, 1=admin, 2=user, may be combined via commas")
    private String menuRight;

    private String menuComponent;

    private String menuIcon;


}
