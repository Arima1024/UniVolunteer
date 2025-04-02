package com.univolunteer.user.domain.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class LoginStatistics {
    private Long id;
    private LocalDateTime LoginDate;
    private Long LoginCount;
}
