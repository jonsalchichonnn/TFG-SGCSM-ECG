package com.sgcsm_ecg.service;

import com.sgcsm_ecg.common.HttpResponse;
import com.sgcsm_ecg.common.UserDTO;
import com.sgcsm_ecg.entity.Pass;
import com.sgcsm_ecg.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  User Service
 * </p>
 *
 * @author sgcsm
 * @since 2023-05-03
 */
public interface UserService extends IService<User> {

//    HttpResponse<List<User>> getUsers(Integer pageNum, Integer pageSize, String name, Integer sex);
    HttpResponse<List<User>> getUsers(UserDTO userDTO);

    HttpResponse<?> createUser(User user);

    HttpResponse<?> updateUser(int id, User user);

    HttpResponse<?> deleteUser(int id);

    HttpResponse<User> getUserById(int id);

    HttpResponse<?> updateUserPass(int id, Pass pass);
}
