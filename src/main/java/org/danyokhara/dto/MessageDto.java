package org.danyokhara.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MessageDto {
    private Integer id;
    private String userName;
    private LocalDateTime dateTime;
    private String text;
}
