package com.univolunteer.common.domain.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("audit_logs")
public class AuditLog {

    @TableId
    private Long id;

    private Long userId;

    private String action;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime timestamp;

    private Integer status;

    private String remark;

}
