package com.sgcsm_ecg.mapper;

import com.sgcsm_ecg.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  User Mapper interface
 * </p>
 *
 * @author sgcsm
 * @since 2023-05-03
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}
