package com.sgcsm_ecg.controller;


import com.sgcsm_ecg.common.HttpResponse;
import com.sgcsm_ecg.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author sgcsm
 * @since 2023-05-13
 */
@RestController
@RequestMapping("/devices")
public class DeviceController {
    @Autowired
    private DeviceService deviceService;

    @GetMapping()
    public HttpResponse<?> getDevices(Integer pageNum, Integer pageSize, String id) {
        return deviceService.getMenu(pageNum, pageSize,id);
    }

}
