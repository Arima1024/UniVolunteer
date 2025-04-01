package com.univolunteer.common.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 活动附件表（activity_assets）
 */
@Data
@TableName("activity_assets")
public class ActivityAsset {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;           // 资源ID

    private Long activityId;   // 所属活动ID
    private String fileUrl;       // 资源文件URL
    private LocalDateTime uploadTime; // 上传时间
}
