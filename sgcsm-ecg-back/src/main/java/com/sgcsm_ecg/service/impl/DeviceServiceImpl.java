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
        List<DeviceDTO> result;
        long total;
        if(pageNum == null && pageSize == null && id == null){
            result = getAllDevices();
            total = (long) result.size();
//            return HttpResponse.success(result, (long) result.size());
        }else{
            LambdaQueryWrapper<Device> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.like(StringUtils.isNotBlank(id),
                    Device::getId, id);
            Page<Device> p = new Page<>(pageNum, pageSize);
            page(p, lambdaQueryWrapper);

            result = toDTOList(p.getRecords());
            total = p.getTotal();
//            return HttpResponse.success(result, p.getTotal());
        }
        return HttpResponse.success(result, total);
    }

    @Override
    public List<DeviceDTO> getAllDevices() {
        List<Device> devList = list();
        return toDTOList(devList);
//        List<DeviceDTO> dtoList = new ArrayList<>(devList.size());
//        for(Device d : devList){
//            // Convert JSON to Object
//            ObjectMapper objectMapper = new ObjectMapper();
//            try {
//                DeviceDTO deviceDTO = objectMapper.readValue(d.getProperties(), DeviceDTO.class);
//                dtoList.add(deviceDTO);
//            } catch (JsonProcessingException e) {
//                e.printStackTrace();
//            }
//        }
//        return dtoList;
    }

    private List<DeviceDTO> toDTOList(List<Device> devList){
        List<DeviceDTO> dtoList = new ArrayList<>(devList.size());
        for(Device d : devList){
            // Convert JSON to Object
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                DeviceDTO deviceDTO = objectMapper.readValue(d.getProperties(), DeviceDTO.class);
                dtoList.add(deviceDTO);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        return dtoList;
    }
}
