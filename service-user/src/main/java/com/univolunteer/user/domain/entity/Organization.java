package com.univolunteer.user.domain.entity;

import lombok.Data;

@Data
public class Organization {
    private Long organizationId;
    private String organizationName;
    private Boolean isSchool;
}
