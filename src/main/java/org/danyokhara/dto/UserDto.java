package org.danyokhara.dto;

import lombok.Data;

@Data
public class UserDto {
    private Integer id;
    private String sessionId;
    private String name;
}
