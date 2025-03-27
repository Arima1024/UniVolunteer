create table admin_db.announcements
(
    id           int auto_increment comment '公告ID'
        primary key,
    title        varchar(100)                       not null comment '公告标题',
    content      text                               not null comment '公告内容',
    target_role  varchar(50)                        null comment '接收角色',
    status       tinyint  default 1                 null comment '发布状态（0=草稿，1=发布）',
    publish_time datetime default CURRENT_TIMESTAMP null comment '发布时间'
)
    comment '公告通知表';

create table admin_db.platform_configs
(
    id               int auto_increment comment '配置ID'
        primary key,
    config_key       varchar(100)                       not null comment '配置键',
    config_value     varchar(255)                       not null comment '配置值',
    description      varchar(255)                       null comment '说明',
    last_update_time datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '更新时间',
    constraint config_key
        unique (config_key)
)
    comment '平台配置项表';

create table admin_db.service_status
(
    id           int auto_increment comment '记录ID'
        primary key,
    service_name varchar(100)                       not null comment '服务名',
    status       varchar(10)                        not null comment '状态：UP/DOWN',
    timestamp    datetime default CURRENT_TIMESTAMP null comment '状态时间',
    details      json                               null comment '详情JSON，如内存、负载等'
)
    comment '服务健康状态表';

