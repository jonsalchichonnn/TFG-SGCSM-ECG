package com.sgcsm_ecg.controller;

import com.sgcsm_ecg.common.HttpResponse;
import com.sgcsm_ecg.entity.User;
import com.sgcsm_ecg.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping("/login")
    public HttpResponse<?> login(@RequestBody User user){
        return loginService.login(user);
    }

//    @PostMapping("/logout")
//    public HttpResponse<?> logout(@RequestBody User user){
//        return loginService.logout(user);
//    }

}
