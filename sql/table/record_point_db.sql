
create table volunteer_records
(
    id                int auto_increment comment '记录ID'
        primary key,
    user_id           int                        not null comment '志愿者用户ID',
    activity_id       int                        not null comment '活动ID',
    hours             decimal(5, 2) default 0.00 null comment '服务时长（小时）',
    completion_status tinyint       default 0    null comment '完成状态（0=未开展，1=开展中，2=已完成，3=已放弃）',
    sign_in_time      datetime                   null comment '签到时间',
    sign_out_time     datetime                   null comment '签退时间',
    constraint uniq_user_activity
        unique (user_id, activity_id)
)
    comment '志愿服务记录表';

create index idx_user
    on volunteer_records (user_id);