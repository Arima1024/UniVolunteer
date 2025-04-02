create table user_db.login_statistics
(
    id          int auto_increment
        primary key,
    login_date  datetime not null,
    login_count int      not null
);

create table user_db.organization
(
    organization_id   int auto_increment
        primary key,
    organization_name varchar(100)      null,
    is_school         tinyint default 1 not null,
    constraint organization_pk
        unique (organization_name)
);

create table user_db.users
(
    id              int auto_increment comment '用户ID'
        primary key,
    username        varchar(50)                        not null comment '用户名',
    password        varchar(255)                       not null comment '加密密码',
    email           varchar(100)                       not null comment '邮箱地址',
    phone           varchar(20)                        not null comment '手机号',
    role            tinyint  default 0                 not null comment '用户主角色(0代表志愿者 1代表招募方 2代表管理员)',
    status          tinyint  default 1                 not null comment '账号状态（1=正常，0=冻结）',
    last_login_time datetime                           null comment '最后登录时间',
    create_time     datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    organization_id int                                not null,
    constraint phone
        unique (phone)
)
    comment '用户基本信息表';

