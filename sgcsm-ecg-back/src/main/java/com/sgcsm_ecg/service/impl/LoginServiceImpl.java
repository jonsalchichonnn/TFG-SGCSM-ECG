package com.sgcsm_ecg.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.sgcsm_ecg.common.HttpResponse;
import com.sgcsm_ecg.entity.User;
import com.sgcsm_ecg.mapper.UserMapper;
import com.sgcsm_ecg.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public HttpResponse<?> login(User user) {
        System.err.println("user.getAccount() = " + user.getAccount() + " user.getPassword()= " + user.getPassword());
        User result = userMapper.selectOne(new LambdaQueryWrapper<User>()
                .eq(User::getAccount, user.getAccount())
                .eq(User::getPassword, user.getPassword()));

        if (result != null) {
            result.setPassword(null);
            System.err.println(result);

            return HttpResponse.success(result);
        }

        return HttpResponse.fail("Username or Password is incorrect ...");
    }
}
