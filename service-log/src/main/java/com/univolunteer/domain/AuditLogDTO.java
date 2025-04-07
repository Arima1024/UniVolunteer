package com.univolunteer.domain;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AuditLogDTO {
    private Long id;

    private String action;

    private LocalDateTime timestamp;

    private Integer status;

    private String remark;

    private String userName;

    private String phone;

    private  Long auditLogId;
}
