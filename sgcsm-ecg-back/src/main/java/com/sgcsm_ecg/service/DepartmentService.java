package com.sgcsm_ecg.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sgcsm_ecg.common.HttpResponse;
import com.sgcsm_ecg.entity.Department;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author sgcsm
 * @since 2023-05-11
 */
public interface DepartmentService extends IService<Department> {

    HttpResponse<List<Department>> getDepartments(Integer pageNum, Integer pageSize, String name, String newName);

    HttpResponse<?> createDepartment(Department department);

    HttpResponse<?> updateDepartment(int id, Department department);

    HttpResponse<?> deleteDepartment(int id);
}
