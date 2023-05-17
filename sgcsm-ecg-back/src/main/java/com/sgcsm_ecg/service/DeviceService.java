package com.sgcsm_ecg.service;

import com.sgcsm_ecg.common.HttpResponse;
import com.sgcsm_ecg.entity.Device;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author sgcsm
 * @since 2023-05-13
 */
public interface DeviceService extends IService<Device> {

    HttpResponse<?> getMenu(Integer pageNum, Integer pageSize, String id);
}
