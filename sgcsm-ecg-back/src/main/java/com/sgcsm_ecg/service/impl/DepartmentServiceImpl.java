package com.sgcsm_ecg.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sgcsm_ecg.common.HttpResponse;
import com.sgcsm_ecg.entity.Department;
import com.sgcsm_ecg.mapper.DepartmentMapper;
import com.sgcsm_ecg.service.DepartmentService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * Department Service Implementation
 * </p>
 *
 * @author sgcsm
 * @since 2023-05-11
 */
@Service
public class DepartmentServiceImpl extends ServiceImpl<DepartmentMapper, Department> implements DepartmentService {

    @Override
    public HttpResponse<List<Department>> getDepartments(Integer pageNum, Integer pageSize, String name, String newName) {
        if (pageNum == null || pageSize == null) {
            List<Department> records = list();
            return HttpResponse.success(records, (long) records.size());
        } else {
            System.err.println("name = " + name + " NEWNAME = " + newName);
            // Filter by name
            LambdaQueryWrapper<Department> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.like(StringUtils.isNotBlank(name),
                    Department::getName, name);
            // check duplicate on insert
            lambdaQueryWrapper.eq(StringUtils.isNotBlank(newName),
                    Department::getName, newName);
            Page<Department> p = new Page<>(pageNum, pageSize);
            page(p, lambdaQueryWrapper);
            System.err.println(p.getRecords());

            return HttpResponse.success(p.getRecords(), p.getTotal());
        }
    }

    @Override
    public HttpResponse<?> createDepartment(Department department) {
        return save(department) ? HttpResponse.created() : HttpResponse.fail("Could not create user");
    }

    @Override
    public HttpResponse<?> updateDepartment(int id, Department department) {
        updateById(department);
        return HttpResponse.success();
    }

    @Override
    public HttpResponse<?> deleteDepartment(int id) {
        removeById(id);
        return HttpResponse.success();
    }


}
