create table point_history
(
    id            int auto_increment comment '记录ID'
        primary key,
    user_id       int                                not null comment '用户ID',
    change_amount int                                not null comment '积分变动值',
    reason        varchar(100)                       null comment '变动原因',
    date          datetime default CURRENT_TIMESTAMP null comment '变动时间'
)
    comment '积分流水记录表';

create index idx_user
    on point_history (user_id);

create table points
(
    user_id      int           not null comment '用户ID'
        primary key,
    total_points int default 0 null comment '累计积分'
)
    comment '用户积分表';

create table volunteer_records
(
    id                 int auto_increment comment '记录ID'
        primary key,
    user_id            int                        not null comment '志愿者用户ID',
    activity_id        int                        not null comment '活动ID',
    hours              decimal(5, 2) default 0.00 null comment '服务时长（小时）',
    completion_status  tinyint       default 0    null comment '完成状态（0=未完成，1=完成，2=放弃）',
    confirm_time       datetime                   null comment '确认时间',
    organizer_feedback varchar(255)               null comment '组织者反馈',
    constraint uniq_user_activity
        unique (user_id, activity_id)
)
    comment '志愿服务记录表';

create index idx_user
    on volunteer_records (user_id);
