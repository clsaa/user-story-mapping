package com.clsaa.homework.usm.service;

import com.clsaa.homework.usm.dao.UserRepository;
import com.clsaa.homework.usm.model.po.User;
import com.clsaa.homework.usm.model.vo.UserV1;
import com.clsaa.homework.usm.util.BeanUtils;
import com.clsaa.homework.usm.util.TimestampUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * @author joyren
 */
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public UserV1 addUser(String username, String password) {
        User user = User.builder()
                .username(username)
                .password(password)
                .ctime(LocalDateTime.now())
                .mtime(LocalDateTime.now())
                .build();
        User existUser = this.userRepository.save(user);
        return BeanUtils.convertType(existUser, UserV1.class);
    }

    public UserV1 findUserById(String id) {
        User existUser = this.userRepository.findUserById(id);
        return BeanUtils.convertType(existUser, UserV1.class);
    }

    public UserV1 findUserByUsername(String username) {
        User existUser = this.userRepository.findUserByUsername(username);
        return BeanUtils.convertType(existUser, UserV1.class);
    }
}
