package com.sgcsm_ecg.controller;


import com.sgcsm_ecg.common.HttpResponse;
import com.sgcsm_ecg.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  Menu controller
 * </p>
 *
 * @author sgcsm
 * @since 2023-05-09
 */
@RestController
@RequestMapping("/menu")
public class MenuController {

    @Autowired
    private MenuService menuService;

    @GetMapping()
    public HttpResponse<?> getMenu(String role) {
        return menuService.getMenu(role);
    }

}
