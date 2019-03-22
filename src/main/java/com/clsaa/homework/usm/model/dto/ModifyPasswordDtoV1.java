package com.clsaa.homework.usm.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author joyren
 */
@Getter
@Setter
public class ModifyPasswordDtoV1 implements Serializable {
    private String email;
    private String oldPassword;
    private String password;
}
