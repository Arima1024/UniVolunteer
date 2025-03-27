create table registration_db.registration_history
(
    id              int auto_increment comment '记录ID'
        primary key,
    registration_id int                                not null comment '报名ID（外键）',
    status_from     tinyint                            not null comment '变更前状态',
    status_to       tinyint                            not null comment '变更后状态',
    change_time     datetime default CURRENT_TIMESTAMP null comment '状态变更时间',
    changed_by      int                                null comment '操作人用户ID'
)
    comment '报名状态变更历史记录表';

create table registration_db.registrations
(
    id            int auto_increment comment '报名ID'
        primary key,
    activity_id   int                                not null comment '活动ID（外键）',
    user_id       int                                not null comment '报名人ID（外键）',
    apply_time    datetime default CURRENT_TIMESTAMP null comment '报名时间',
    status        tinyint  default 0                 null comment '报名状态（0=申请，1=通过，2=拒绝，3=取消）',
    approval_time datetime                           null comment '审批时间',
    approver_id   int                                null comment '审批人ID'
)
    comment '志愿者报名表';

create index idx_activity
    on registration_db.registrations (activity_id);

create index idx_user
    on registration_db.registrations (user_id);

