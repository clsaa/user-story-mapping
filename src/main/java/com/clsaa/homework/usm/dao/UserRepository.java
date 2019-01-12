package com.clsaa.homework.usm.dao;

import com.clsaa.homework.usm.model.po.User;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author joyren
 */
public interface UserRepository extends MongoRepository<User, String> {

    /**
     * 根据用户名查询用户
     *
     * @param username 用户名
     * @return {@link User}
     */
    User findUserByUsername(String username);

    /**
     * 根据用户Email查询用户
     * @param email 用户邮箱
     * @return {@link User}
     */
    User findUserByEmail(String email);
}
