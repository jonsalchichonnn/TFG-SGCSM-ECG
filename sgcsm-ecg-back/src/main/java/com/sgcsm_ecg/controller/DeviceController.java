package com.sgcsm_ecg.controller;


import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import com.sgcsm_ecg.common.HttpResponse;
import com.sgcsm_ecg.entity.Device;
import com.sgcsm_ecg.entity.DeviceDTO;
import com.sgcsm_ecg.service.DeviceService;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

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
        return deviceService.getDevices(pageNum, pageSize,id);
    }

    @GetMapping(value = "/export", produces = "application/octet-stream")
    public void exportEmployee(HttpServletResponse response) {
        List<DeviceDTO> dtoList = deviceService.getAllDevices();
        // HSSF 03 version (xls) for better compatibility
        ExportParams params = new ExportParams("Devices", "Devices", ExcelType.HSSF);
        // export util from easy poi
        Workbook workbook = ExcelExportUtil.exportExcel(params, DeviceDTO.class, dtoList);
        ServletOutputStream out = null;
        try {
            // 流形式导出
            response.setHeader("content-type", "application/octet-stream");
            // 防止中文乱码
            response.setHeader("content-disposition", "attachment;filename=Devices.xls");
            out = response.getOutputStream();
            workbook.write(out);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != out) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
