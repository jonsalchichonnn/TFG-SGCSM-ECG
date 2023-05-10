package com.sgcsm_ecg.service;

import com.sgcsm_ecg.common.HttpResponse;
import com.sgcsm_ecg.entity.Menu;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author sgcsm
 * @since 2023-05-09
 */
public interface MenuService extends IService<Menu> {

    HttpResponse<?> getMenu(String role);
}
