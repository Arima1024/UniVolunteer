create table activity_db.activities
(
    id                 int auto_increment comment '活动ID'
        primary key,
    title              varchar(100)                       not null comment '活动标题',
    description        text                               null comment '活动详细描述',
    user_id            int                                not null comment '发布人用户ID（外键）',
    category           varchar(50)                        null comment '活动类别',
    location           varchar(100)                       null comment '活动地点',
    start_time         datetime                           not null comment '开始时间',
    end_time           datetime                           not null comment '结束时间',
    max_volunteers     int      default 100               null comment '最大志愿者数',
    status             tinyint  default 1                 null comment '状态（0=草稿，1=审核中，2=招募中，3=已结束）',
    create_time        datetime default CURRENT_TIMESTAMP null comment '创建时间',
    currentSignUpCount int      default 0                 not null comment '当前报名人数',
    sign_up_start_time datetime default CURRENT_TIMESTAMP null comment '报名开始时间',
    sign_up_end_time   datetime default CURRENT_TIMESTAMP null comment '报名截止时间',
    audit_time         datetime                           null
)
    comment '志愿活动基本信息表';

create index idx_category
    on activity_db.activities (category);

create index idx_status
    on activity_db.activities (status);

create table activity_db.activity_assets
(
    id          int auto_increment comment '资源ID'
        primary key,
    activity_id int                                not null comment '所属活动ID（外键）',
    file_url    varchar(255)                       not null comment '资源文件URL',
    upload_time datetime default CURRENT_TIMESTAMP null comment '上传时间',
    constraint activity_assets_ibfk_1
        foreign key (activity_id) references activity_db.activities (id)
            on delete cascade
)
    comment '活动图片/附件资源表';

create index activity_id
    on activity_db.activity_assets (activity_id);

