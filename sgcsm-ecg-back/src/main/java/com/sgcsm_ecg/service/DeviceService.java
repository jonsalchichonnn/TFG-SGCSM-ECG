package com.sgcsm_ecg.service;

import com.sgcsm_ecg.common.HttpResponse;
import com.sgcsm_ecg.entity.Device;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sgcsm_ecg.entity.DeviceDTO;

import java.util.List;

/**
 * <p>
 * Device Service
 * </p>
 *
 * @author sgcsm
 * @since 2023-05-13
 */
public interface DeviceService extends IService<Device> {

    HttpResponse<?> getDevices(Integer pageNum, Integer pageSize, String id);

    List<DeviceDTO> getAllDevices();
}
