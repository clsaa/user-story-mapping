package com.clsaa.homework.usm.model.po;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * @author joyren
 */
@Document(collection = "user")
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @Field("id")
    private String id;
    @Field("username")
    private String username;
    @Field("password")
    private String password;
    @Field("email")
    private String email;
    @Field("nickname")
    private String nickname;
    @Field("ctime")
    private Timestamp ctime;
    @Field("mtime")
    private Timestamp mtime;
}
