package com.clsaa.homework.usm.model.po;

import com.clsaa.homework.usm.enums.UserStoryMappingStatusEnum;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

/**
 * @author joyren
 */
@Document(collection = "user_story_mapping")
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserStoryMapping {
    @Id
    @Field("id")
    private String id;
    @Field("username")
    private String name;
    @Field("description")
    private String description;
    @Field("data")
    private String data;
    @Field("cuser")
    private String cuser;
    @Field("ctime")
    private LocalDateTime ctime;
    @Field("mtime")
    private LocalDateTime mtime;
    @Field("status")
    private UserStoryMappingStatusEnum status;
}
