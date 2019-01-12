package com.clsaa.homework.usm.model.dto;

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
}
