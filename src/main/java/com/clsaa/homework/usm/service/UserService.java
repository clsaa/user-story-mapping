package com.clsaa.homework.usm.service;

import com.clsaa.homework.usm.config.BizCodes;
import com.clsaa.homework.usm.dao.UserRepository;
import com.clsaa.homework.usm.model.po.User;
import com.clsaa.homework.usm.model.vo.UserV1;
import com.clsaa.homework.usm.util.BeanUtils;
import com.clsaa.rest.result.bizassert.BizAssert;
import com.clsaa.rest.result.bizassert.BizCode;
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

    public UserV1 addUser(String username, String password, String email, String nickname) {
        BizAssert.validParam(this.userRepository.findUserByUsername(username) == null, BizCodes.DUPLICATED_USERNAME);
        BizAssert.validParam(this.userRepository.findUserByEmail(email) == null, BizCodes.DUPLICATED_EMAIL);
        User user = User.builder()
                .username(username)
                .password(password)
                .email(email)
                .nickname(nickname)
                .ctime(LocalDateTime.now())
                .mtime(LocalDateTime.now())
                .build();
        User existUser = this.userRepository.save(user);
        existUser.setPassword("");
        return BeanUtils.convertType(existUser, UserV1.class);
    }

    public UserV1 findUserByUsername(String username) {
        User existUser = this.userRepository.findUserByUsername(username);
        return BeanUtils.convertType(existUser, UserV1.class);
    }

    public UserV1 findUserByEmail(String email) {
        User existUser = this.userRepository.findUserByEmail(email);
        return BeanUtils.convertType(existUser, UserV1.class);
    }

    public UserV1 updatePassword(String email, String oldPassword, String password) {
        User existUser = this.userRepository.findUserByEmail(email);
        if (existUser.getPassword().equals(oldPassword)) {
            existUser.setMtime(LocalDateTime.now());
            existUser.setPassword(password);
            this.userRepository.save(existUser);
        } else {
            BizAssert.justDenied(new BizCode(10001, "邮箱、密码错误"));
        }
        existUser.setPassword("");
        return BeanUtils.convertType(existUser, UserV1.class);
    }
}
