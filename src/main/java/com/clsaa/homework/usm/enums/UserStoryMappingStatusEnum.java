package com.clsaa.homework.usm.enums;

/**
 * @author joyren
 */

public enum UserStoryMappingStatusEnum {
    /**
     * 已删除
     */
    DELETED("DELETED"),
    /**
     * 正常状态
     */
    OK("OK");
    private final String code;

    UserStoryMappingStatusEnum(String code) {
        this.code = code;
    }
}