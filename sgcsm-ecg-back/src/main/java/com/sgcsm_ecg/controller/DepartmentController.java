package com.sgcsm_ecg.controller;


import com.sgcsm_ecg.common.HttpResponse;
import com.sgcsm_ecg.entity.Department;
import com.sgcsm_ecg.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * Department controller
 * </p>
 *
 * @author sgcsm
 * @since 2023-05-11
 */
@RestController
@RequestMapping("/departments")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @GetMapping()
    public HttpResponse<List<Department>> getDepartments(Integer pageNum, Integer pageSize, String name, String newName) {
        return departmentService.getDepartments(pageNum, pageSize, name, newName);
    }

    @PostMapping()
    public HttpResponse<?> createDepartment(@RequestBody Department department) {
        return departmentService.createDepartment(department);
    }

    @PutMapping("{id}")
    public HttpResponse<?> updateDepartment(@PathVariable int id, @RequestBody Department department) {
        return departmentService.updateDepartment(id, department);
    }

    @DeleteMapping("/{id}")
    public HttpResponse<?> deleteDepartment(@PathVariable int id) {
        return departmentService.deleteDepartment(id);
    }
}
