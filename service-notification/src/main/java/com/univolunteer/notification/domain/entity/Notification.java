package com.univolunteer.notification.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import java.util.Date;

@TableName("notifications")  // 映射数据库中的表
public class Notification {

    @TableId(value = "id", type = IdType.AUTO)  // 自增主键
    private Integer id;

    @TableField("user_id")  // 映射数据库中的 user_id
    private Integer userId;

    @TableField("sender_id")  // 映射数据库中的 sender_id
    private Integer senderId;

    @TableField("message")  // 映射数据库中的 message
    private String message;

    @TableField("status")  // 映射数据库中的 status
    private Integer status;

    @TableField("create_time")  // 映射数据库中的 create_time
    private Date createTime;

    // Getter 和 Setter 方法

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getSenderId() {
        return senderId;
    }

    public void setSenderId(Integer senderId) {
        this.senderId = senderId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
