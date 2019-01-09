package com.clsaa.homework.usm.model.vo;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.sql.Timestamp;
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
public class UserV1 {
    private String id;
    private String username;
    private String password;
    private String email;
    private String nickname;
    private String avatarUrl;
    private LocalDateTime ctime;
    private LocalDateTime mtime;
}
