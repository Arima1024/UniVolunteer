create table statistics_db.log_summary
(
    id         int auto_increment comment '记录ID'
        primary key,
    event_type varchar(50)   not null comment '事件类型',
    count      int default 0 null comment '事件发生次数',
    date       date          not null comment '统计日期'
)
    comment '日志事件汇总表';

create index idx_event_date
    on statistics_db.log_summary (event_type, date);

create table statistics_db.statistics_metrics
(
    id                        int auto_increment comment '记录ID'
        primary key,
    metric_date               date                        not null comment '统计日期',
    new_users                 int            default 0    null comment '新注册用户数',
    active_volunteers         int            default 0    null comment '活跃志愿者数',
    new_activities            int            default 0    null comment '新发布活动数',
    participations            int            default 0    null comment '报名次数',
    completed_volunteer_hours decimal(10, 2) default 0.00 null comment '总完成志愿小时',
    average_rating            decimal(3, 2)  default 0.00 null comment '平均评分'
)
    comment '统计指标结果表';

create index idx_metric_date
    on statistics_db.statistics_metrics (metric_date);

