package com.sgcsm_ecg.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sgcsm_ecg.common.HttpResponse;
import com.sgcsm_ecg.entity.DeviceDTO;
import com.sgcsm_ecg.entity.Log;
import com.sgcsm_ecg.mapper.LogMapper;
import com.sgcsm_ecg.service.LogService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author sgcsm
 * @since 2023-05-20
 */
@Service
public class LogServiceImpl extends ServiceImpl<LogMapper, Log> implements LogService {

    @Override
    public void saveDevLog(DeviceDTO device, String peerHost) {
        Log log = new Log();
        log.setDeviceId(device.getId());
        log.setIp(peerHost);
        log.setCreateTime(LocalDateTime.now());
        save(log);
    }

    @Override
    public HttpResponse<List<Log>> getLogs(Integer pageNum, Integer pageSize, String deviceId) {
        LambdaQueryWrapper<Log> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.like(StringUtils.isNotBlank(deviceId),
                Log::getDeviceId, deviceId);

        Page<Log> p = new Page<>(pageNum, pageSize);
        page(p, lambdaQueryWrapper);

        return HttpResponse.success(p.getRecords(), p.getTotal());
    }
}
