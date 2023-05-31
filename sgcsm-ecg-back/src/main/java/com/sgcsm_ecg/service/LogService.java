package com.sgcsm_ecg.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sgcsm_ecg.common.HttpResponse;
import com.sgcsm_ecg.entity.DeviceDTO;
import com.sgcsm_ecg.entity.Log;

import java.util.List;

/**
 * <p>
 * Log Service
 * </p>
 *
 * @author sgcsm
 * @since 2023-05-20
 */
public interface LogService extends IService<Log> {

    void saveDevLog(DeviceDTO deviceDTO, String peerHost);

    HttpResponse<List<Log>> getLogs(Integer pageNum, Integer pageSize, String deviceId);

}
