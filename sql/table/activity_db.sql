CREATE TABLE `activities` (
                              `id` int NOT NULL AUTO_INCREMENT COMMENT '活动ID',
                              `title` varchar(100) COLLATE utf8mb4_general_ci NOT NULL COMMENT '活动标题',
                              `description` text COLLATE utf8mb4_general_ci COMMENT '活动详细描述',
                              `organizer_id` int NOT NULL COMMENT '发布人用户ID（外键）',
                              `category` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '活动类别',
                              `location` varchar(100) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '活动地点',
                              `start_time` datetime NOT NULL COMMENT '开始时间',
                              `end_time` datetime NOT NULL COMMENT '结束时间',
                              `max_volunteers` int DEFAULT '100' COMMENT '最大志愿者数',
                              `status` tinyint DEFAULT '1' COMMENT '状态（0=草稿，1=审核中，2=招募中，3=已结束）',
                              `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                              `currentSignUpCount` int NOT NULL COMMENT '当前报名人数',
                              PRIMARY KEY (`id`),
                              KEY `idx_organizer` (`organizer_id`),
                              KEY `idx_category` (`category`),
                              KEY `idx_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='志愿活动基本信息表';

CREATE TABLE `activity_assets` (
                                   `id` int NOT NULL AUTO_INCREMENT COMMENT '资源ID',
                                   `activity_id` int NOT NULL COMMENT '所属活动ID（外键）',
                                   `file_url` varchar(255) COLLATE utf8mb4_general_ci NOT NULL COMMENT '资源文件URL',
                                   `upload_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '上传时间',
                                   PRIMARY KEY (`id`),
                                   KEY `activity_id` (`activity_id`),
                                   CONSTRAINT `activity_assets_ibfk_1` FOREIGN KEY (`activity_id`) REFERENCES `activities` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='活动图片/附件资源表';

CREATE TABLE `activity_categories` (
                                       `id` int NOT NULL AUTO_INCREMENT COMMENT '分类ID',
                                       `name` varchar(50) COLLATE utf8mb4_general_ci NOT NULL COMMENT '活动类别名称',
                                       `description` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '类别说明',
                                       PRIMARY KEY (`id`),
                                       UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='活动分类表'

