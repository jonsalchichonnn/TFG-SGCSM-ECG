package com.sgcsm_ecg.controller;


import com.sgcsm_ecg.common.HttpResponse;
import com.sgcsm_ecg.common.UserDTO;
import com.sgcsm_ecg.entity.Pass;
import com.sgcsm_ecg.entity.User;
import com.sgcsm_ecg.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * User Controller
 * </p>
 *
 * @author sgcsm
 * @since 2023-05-03
 */
@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

//    @GetMapping()
//    public HttpResponse<List<User>> getUsers(Integer pageNum, Integer pageSize, String name, Integer sex) {
//        return userService.getUsers(pageNum, pageSize, name, sex);

//    }

    @GetMapping()
    public HttpResponse<List<User>> getUsers(UserDTO userDTO) {
        return userService.getUsers(userDTO);
    }

    @GetMapping("{id}")
    public HttpResponse<User> getUserById(@PathVariable int id) {
        return userService.getUserById(id);
    }

    @PostMapping()
    public HttpResponse<?> createUser(@RequestBody User user){
        return userService.createUser(user);
    }

    @PutMapping("{id}")
    public HttpResponse<?> updateUser(@PathVariable int id, @RequestBody User user){
        return userService.updateUser(id, user);
    }

    @PutMapping("{id}/pass")
    public HttpResponse<?> updateUserPass(@PathVariable int id, @RequestBody Pass pass){
        return userService.updateUserPass(id, pass);
    }

    @DeleteMapping("{id}")
    public HttpResponse<?> deleteUser(@PathVariable int id){
        return userService.deleteUser(id);
    }


}
