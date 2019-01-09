package com.clsaa.homework.usm.model.vo;

import lombok.*;

/**
 * @author joyren
 */
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginUserV1 {
    private String id;
    private String username;
    private String password;
    private String email;
    private String nickname;
    private String avatarUrl;
    private String token;
}
