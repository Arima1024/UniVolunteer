package com.univolunteer.notification.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@TableName("notifications")  // 映射数据库中的表
@Data
public class Notification {

    @TableId(value = "id", type = IdType.AUTO)  // 自增主键
    private Long id;

    @TableField("user_id")  // 映射数据库中的 user_id
    private Long userId;//收件人

    @TableField("sender_id")  // 映射数据库中的 sender_id
    private Long senderId;//发件人

    @TableField("message")  // 映射数据库中的 message
    private String message;

    @TableField("status")  // 映射数据库中的 status
    private Integer status;

    @TableField("create_time")  // 映射数据库中的 create_time
    private LocalDateTime createTime;

    @TableField("activity_id")  // 映射数据库中的 create_time
    private Long activityId;
<<<<<<< HEAD

    @TableField("type")  // 映射数据库中的 create_time
    private Integer type;
=======
>>>>>>> origin/dev
}
