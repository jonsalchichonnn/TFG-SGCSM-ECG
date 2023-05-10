package com.sgcsm_ecg.service;

import com.sgcsm_ecg.common.HttpResponse;
import com.sgcsm_ecg.entity.User;

public interface LoginService {
    HttpResponse<?> login(User user);
}
