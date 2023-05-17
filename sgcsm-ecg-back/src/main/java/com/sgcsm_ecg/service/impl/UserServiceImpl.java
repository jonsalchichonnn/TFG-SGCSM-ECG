package com.sgcsm_ecg.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sgcsm_ecg.common.HttpResponse;
import com.sgcsm_ecg.common.UserDTO;
import com.sgcsm_ecg.entity.User;
import com.sgcsm_ecg.mapper.UserMapper;
import com.sgcsm_ecg.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * User Service Implementation
 * </p>
 *
 * @author sgcsm
 * @since 2023-05-03
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    //    @Override
    public HttpResponse<List<User>> getUsers(Integer pageNum, Integer pageSize, String name, Integer sex) {
        List<User> records;
        if (pageNum == null || pageSize == null) {
            records = list();
            return HttpResponse.success(records, (long) records.size());
        } else {
            LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.like(StringUtils.isNotBlank(name),
                    User::getName, name);
            lambdaQueryWrapper.eq(sex != null && sex >= 0 && sex <= 1,
                    User::getSex, sex);

            Page<User> p = new Page<>(pageNum, pageSize);
            page(p, lambdaQueryWrapper);
            return HttpResponse.success(p.getRecords(), p.getTotal());
//            records = p.getRecords();
//            return HttpResponse.success(records, (long) records.size());
        }

    }

    @Override
    public HttpResponse<List<User>> getUsers(UserDTO dto) {
        List<User> records;
        if (dto.allNull()) {
            records = list();
            return HttpResponse.success(records, (long) records.size());
        } else {
            LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            // check duplicate accounts
            lambdaQueryWrapper.eq(StringUtils.isNotBlank(dto.getAccount()),
                    User::getAccount, dto.getAccount());

            lambdaQueryWrapper.like(StringUtils.isNotBlank(dto.getName()),
                    User::getName, dto.getName());
            // filter by sex
            Integer sex = dto.getSex();
            lambdaQueryWrapper.eq(sex != null && sex >= 0 && sex <= 1,
                    User::getSex, sex);

            // filter by role
            Integer role = dto.getRole();
            lambdaQueryWrapper.eq(role != null && role >= 0 && role <= 2,
                    User::getRole, role);

            Page<User> p = new Page<>(dto.getPageNum(), dto.getPageSize());
            page(p, lambdaQueryWrapper);
            return HttpResponse.success(p.getRecords(), p.getTotal());
//            records = p.getRecords();
//            return HttpResponse.success(records, (long) records.size());
        }
    }

    @Override
    public HttpResponse<?> createUser(User user) {
        return save(user) ? HttpResponse.created() : HttpResponse.fail("Could not create user");
    }

    @Override
    public HttpResponse<?> updateUser(int id, User user) {
        if (StringUtils.isBlank(user.getPassword())) {
            String oldPass = getById(id).getPassword();
            user.setPassword(oldPass);
        }
        updateById(user);
        return HttpResponse.success();
    }

    @Override
    public HttpResponse<?> deleteUser(int id) {

        removeById(id);
        return HttpResponse.success();
    }
}
