package com.clsaa.homework.usm.controller;

import com.clsaa.homework.usm.model.dto.UserDtoV1;
import com.clsaa.homework.usm.model.vo.UserV1;
import com.clsaa.homework.usm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author joyren
 */
@CrossOrigin
@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/v1/users")
    public UserV1 addUserV1(@RequestBody UserDtoV1 userDtoV1) {
        return this.userService.addUser(userDtoV1.getUsername(), userDtoV1.getPassword());
    }

    @GetMapping("/v1/users/{id}")
    public UserV1 findUserByIdV1(@PathVariable("id") String id){
        return this.userService.findUserById(id);
    }
}
