package com.univolunteer.user.domain.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class Organization {
    @TableId // ✅ 主键注解不能漏！
    private Long organizationId;
    private String organizationName;
    private Boolean isSchool;
}
