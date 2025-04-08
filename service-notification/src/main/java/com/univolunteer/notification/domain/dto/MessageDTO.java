package com.univolunteer.notification.domain.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MessageDTO {
    private Long id;
    private Integer type;
    private String username;
    private LocalDateTime sendTime;
}
