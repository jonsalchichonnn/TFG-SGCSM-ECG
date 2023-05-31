package com.sgcsm_ecg.service.impl;

import com.sgcsm_ecg.common.HttpResponse;
import com.sgcsm_ecg.entity.Menu;
import com.sgcsm_ecg.mapper.MenuMapper;
import com.sgcsm_ecg.service.MenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  Menu Service Implementation
 * </p>
 *
 * @author sgcsm
 * @since 2023-05-09
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

    @Override
    public HttpResponse<?> getMenu(String role) {
        List<Menu> menus = lambdaQuery().like(Menu::getMenuRight, role).list();
        return HttpResponse.success(menus);
    }
}
