create table log_db.audit_logs
(
    id         int auto_increment comment '日志ID'
        primary key,
    user_id    int                                null comment '操作者用户ID',
    action     varchar(100)                       not null comment '操作描述',
    timestamp  datetime default CURRENT_TIMESTAMP null comment '时间戳',
    ip_address varchar(45)                        null comment 'IP地址',
    status     tinyint  default 1                 null comment '状态（1=成功，0=失败）',
    remark     varchar(255)                       null comment '备注'
)
    comment '操作审计日志表';

create index idx_user_action
    on log_db.audit_logs (user_id, action);

create table log_db.blacklist
(
    id          int auto_increment comment '记录ID'
        primary key,
    type        enum ('IP', 'USER') not null comment '封禁类型',
    value       varchar(100)        not null comment '封禁值',
    reason      varchar(255)        null comment '封禁原因',
    expire_time datetime            null comment '过期时间'
)
    comment '黑名单表';

create index idx_type_value
    on log_db.blacklist (type, value);

create table log_db.security_events
(
    id          int auto_increment comment '事件ID'
        primary key,
    event_type  varchar(50)                        not null comment '事件类型，如LOGIN_FAIL等',
    detail      text                               null comment '事件详情',
    create_time datetime default CURRENT_TIMESTAMP null comment '创建时间'
)
    comment '安全事件表';

