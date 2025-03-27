create table checkin_db.checkins
(
    id           int auto_increment comment '签到ID'
        primary key,
    activity_id  int                                not null comment '活动ID',
    user_id      int                                not null comment '用户ID',
    checkin_time datetime default CURRENT_TIMESTAMP null comment '签到时间',
    status       tinyint  default 0                 null comment '签到状态（0=正常，1=重复，2=迟到等）',
    remark       varchar(255)                       null comment '备注信息',
    constraint uniq_activity_user
        unique (activity_id, user_id)
)
    comment '签到记录表';

create table checkin_db.devices
(
    id               int auto_increment comment '设备ID'
        primary key,
    user_id          int                                   not null comment '用户ID',
    device_token     varchar(255)                          not null comment '设备令牌',
    device_type      varchar(50) default 'mobile'          null comment '设备类型，如 mobile/web',
    last_active_time datetime    default CURRENT_TIMESTAMP null comment '最后活跃时间'
)
    comment '设备信息表';

