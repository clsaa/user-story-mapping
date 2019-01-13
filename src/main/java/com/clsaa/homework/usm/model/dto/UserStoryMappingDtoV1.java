package com.clsaa.homework.usm.model.dto;

import com.clsaa.homework.usm.enums.UserStoryMappingStatusEnum;
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
public class UserStoryMappingDtoV1 {
    private String name;
    private String description;
    private String data;
    private UserStoryMappingStatusEnum status;
}
