create table comments
(
    id          int auto_increment comment '评论ID'
        primary key,
    activity_id int                                not null comment '活动ID',
    user_id     int                                not null comment '评论者用户ID',
    rating      tinyint                            null comment '评分（1-5星）',
    content     text                               null comment '评论内容',
    create_time datetime default CURRENT_TIMESTAMP null comment '评论时间',
    status      tinyint                            null comment '0=待评价,1=已评价'
)
    comment '活动评论表';

create index idx_activity
    on comments (activity_id);

create index idx_user
    on comments (user_id);

create table feedback
(
    id          int auto_increment comment '反馈ID'
        primary key,
    user_id     int                                null comment '反馈人用户ID，可为NULL表示匿名',
    title       varchar(100)                       not null comment '反馈标题',
    content     text                               not null comment '反馈内容',
    create_time datetime default CURRENT_TIMESTAMP null comment '创建时间',
    status      tinyint  default 0                 null comment '处理状态（0=新提交，1=已受理，2=已解决）',
    handler_id  int                                null comment '处理人管理员ID'
)
    comment '平台反馈意见表';

create index idx_status
    on feedback (status)