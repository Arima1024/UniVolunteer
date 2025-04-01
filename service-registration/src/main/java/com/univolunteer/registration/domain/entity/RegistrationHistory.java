package com.univolunteer.registration.domain.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("registration_history")
public class RegistrationHistory {

    @TableId
    private Long id;  // 记录ID

    @TableField("registration_id")
    private Long registrationId;  // 报名ID（外键）

    @TableField("status_from")
    private Integer statusFrom;  // 变更前状态

    @TableField("status_to")
    private Integer statusTo;  // 变更后状态

    @TableField(value = "change_time", fill = FieldFill.INSERT)
    private LocalDateTime changeTime;  // 状态变更时间

    @TableField("changed_by")
    private Long changedBy;  // 操作人用户ID

    @TableField("reason")
    private String reason;
}
