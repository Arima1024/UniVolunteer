CREATE TABLE `registration_history` (
                                        `id` int NOT NULL AUTO_INCREMENT COMMENT '记录ID',
                                        `registration_id` int NOT NULL COMMENT '报名ID（外键）',
                                        `status_from` tinyint NOT NULL DEFAULT '0' COMMENT '变更前状态',
                                        `status_to` tinyint NOT NULL DEFAULT '0' COMMENT '变更后状态',
                                        `change_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '状态变更时间',
                                        `changed_by` int DEFAULT NULL COMMENT '操作人用户ID',
                                        `reason` text COLLATE utf8mb4_general_ci,
                                        PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='报名状态变更历史记录表';

CREATE TABLE `registrations` (
                                 `id` int NOT NULL AUTO_INCREMENT COMMENT '报名ID',
                                 `activity_id` int NOT NULL COMMENT '活动ID（外键）',
                                 `user_id` int NOT NULL COMMENT '报名人ID（外键）',
                                 `apply_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '报名时间',
                                 `status` tinyint DEFAULT '0' COMMENT '报名状态（0=申请，1=通过，2=拒绝，3=取消）',
                                 `approval_time` datetime DEFAULT NULL COMMENT '审批时间',
                                 `approver_id` int DEFAULT NULL COMMENT '审批人ID',
                                 PRIMARY KEY (`id`),
                                 KEY `idx_activity` (`activity_id`),
                                 KEY `idx_user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='志愿者报名表'

