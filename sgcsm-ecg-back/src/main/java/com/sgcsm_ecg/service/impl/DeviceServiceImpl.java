package com.sgcsm_ecg.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sgcsm_ecg.common.HttpResponse;
import com.sgcsm_ecg.entity.Device;
import com.sgcsm_ecg.entity.DeviceDTO;
import com.sgcsm_ecg.mapper.DeviceMapper;
import com.sgcsm_ecg.service.DeviceService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author sgcsm
 * @since 2023-05-13
 */
@Service
public class DeviceServiceImpl extends ServiceImpl<DeviceMapper, Device> implements DeviceService {

    @Override
    public HttpResponse<?> getDevices(Integer pageNum, Integer pageSize, String id) {
        LambdaQueryWrapper<Device> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.like(StringUtils.isNotBlank(id),
                Device::getId, id);
        Page<Device> p = new Page<>(pageNum, pageSize);
        page(p, lambdaQueryWrapper);

        // HACE FALTA CONVERTIR JSON A OBJETOS Y MANDARLOS?
        List<DeviceDTO> result = new ArrayList<>();
        for (Device d : p.getRecords()) {
            // Convert JSON to Object
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                DeviceDTO deviceDTO = objectMapper.readValue(d.getProperties(), DeviceDTO.class);
                result.add(deviceDTO);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        return HttpResponse.success(result, p.getTotal());
//        List<Device> menus = lambdaQuery().like(Device::getId, id).list();
//        return HttpResponse.success(menus);
    }
}
