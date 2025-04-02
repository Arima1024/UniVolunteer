package com.univolunteer.registration.domain.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("registrations")
public class Registration {

    @TableId
    private Long id;  // 报名ID

    @TableField("activity_id")
    private Long activityId;  // 活动ID（外键）

    @TableField("user_id")
    private Long userId;  // 报名人ID（外键）

    @TableField(value = "apply_time", fill = FieldFill.INSERT)
    private LocalDateTime applyTime;  // 报名时间

    @TableField("status")
    private Integer status = 0;  // 报名状态（0=申请，1=通过，2=拒绝，3=取消）

    @TableField("approval_time")
    private LocalDateTime approvalTime;  // 审批时间

    @TableField("approver_id")
    private Long approverId;  // 审批人ID
}
