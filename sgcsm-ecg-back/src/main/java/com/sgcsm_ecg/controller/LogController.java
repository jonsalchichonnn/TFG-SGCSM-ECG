package com.sgcsm_ecg.controller;


import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import com.sgcsm_ecg.common.HttpResponse;
import com.sgcsm_ecg.entity.Log;
import com.sgcsm_ecg.service.LogService;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * <p>
 * Logs controller
 * </p>
 *
 * @author sgcsm
 * @since 2023-05-20
 */
@RestController
@RequestMapping("/logs")
public class LogController {

    @Autowired
    private LogService logService;

    @GetMapping()
    public HttpResponse<List<Log>> getLogs(Integer pageNum, Integer pageSize, String deviceId) {
        return logService.getLogs(pageNum, pageSize, deviceId);
    }

    @GetMapping(value = "/export", produces = "application/octet-stream")
    public void exportLogs(HttpServletResponse response) {
        List<Log> logList = logService.list();
        // HSSF 03 version (xls) for better compatibility
        ExportParams params = new ExportParams("System Logs", "Logs", ExcelType.HSSF);
        // export util from easy poi
        Workbook workbook = ExcelExportUtil.exportExcel(params, Log.class, logList);
        ServletOutputStream out = null;
        try {
            // export as a stream
            response.setHeader("content-type", "application/octet-stream");
            response.setHeader("content-disposition", "attachment;filename=Logs.xls");
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
