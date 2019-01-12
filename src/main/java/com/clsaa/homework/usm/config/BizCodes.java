package com.clsaa.homework.usm.config;

import com.clsaa.rest.result.bizassert.BizCode;

/**
 * @author 任贵杰
 */
public interface BizCodes {
    /**
     * 非法请求
     */
    BizCode INVALID_PARAM = new BizCode(1000, "请求参数非法");
    /**
     * 非法请求
     */
    BizCode NOT_FOUND = new BizCode(1001, "数据不存在");
    /**
     * 数据库插入失败
     */
    BizCode ERROR_INSERT = new BizCode(1010, "新增失败");
    /**
     * 数据库删除失败
     */
    BizCode ERROR_DELETE = new BizCode(1011, "删除失败");
    /**
     * 数据库更新失败
     */
    BizCode ERROR_UPDATE = new BizCode(1012, "更新失败");
    /**
     * 用户名密码错误
     */
    BizCode INVALID_LOGIN = new BizCode(1013, "用户名密码错误");
    /**
     * 用户名已存在
     */
    BizCode DUPLICATED_USERNAME = new BizCode(1014, "用户名已存在");
    /**
     * 邮箱已存在
     */
    BizCode DUPLICATED_EMAIL = new BizCode(1015, "邮箱已存在");
    /**
     * 用户无权操作此用户故事地图
     */
    BizCode INVALID_USER = new BizCode(1016, "用户无权操作此用户故事地图");
}