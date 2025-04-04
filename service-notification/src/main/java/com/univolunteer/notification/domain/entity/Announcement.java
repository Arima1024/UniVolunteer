package com.univolunteer.notification.domain.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("announcements")
public class Announcement {

    // 公告ID
    @TableId
    private Integer id;

    // 公告标题
    private String title;

    // 公告内容
    private String content;

    // 接收角色
    private String targetRole;

    // 发布状态（0=草稿，1=发布）
    private Integer status;

    // 发布时间
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime publishTime;

}
