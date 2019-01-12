package com.clsaa.homework.usm.model.vo;

import com.clsaa.homework.usm.enums.UserStoryMappingStatusEnum;
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
public class UserStoryMappingV1 {
    private String id;
    private String name;
    private String description;
    private String data;
    private String cuser;
    private LocalDateTime ctime;
    private LocalDateTime mtime;
    private UserStoryMappingStatusEnum status;
}
