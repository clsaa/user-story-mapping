package com.clsaa.homework.usm.model.dto;

import lombok.*;

import java.time.LocalDateTime;

/**
 * @author joyren
 */
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDtoV1 {
    private String username;
    private String password;
    private String email;
    private String nickname;
    private String avatarUrl;
}
