package com.sgcsm_ecg.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 *  User entity
 * </p>
 *
 * @author sgcsm
 * @since 2023-05-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="UserObject", description="")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String account;

    private String name;

    private String password;

    private Integer age;

    private Integer sex;

    private String phone;

    private Integer role;

    private String valid;


}
