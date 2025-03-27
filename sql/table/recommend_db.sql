create table recommend_db.recommend_rules
(
    id        int auto_increment comment '规则ID'
        primary key,
    rule_name varchar(50) not null comment '规则名称',
    params    json        null comment '规则参数（JSON）'
)
    comment '推荐策略规则表';

create table recommend_db.user_preferences
(
    user_id         int                                not null comment '用户ID'
        primary key,
    preference_tags varchar(255)                       null comment '兴趣标签（逗号分隔）',
    last_updated    datetime default CURRENT_TIMESTAMP null comment '上次更新时间'
)
    comment '用户偏好表';

