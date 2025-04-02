create table notifications
(
    id          int auto_increment
        primary key,
    user_id     int                                null,
    sender_id   int                                null,
    message     text                               null,
    status      tinyint  default 0                 null,
    create_time datetime default CURRENT_TIMESTAMP null,
    activity_id int                                null,
    type        int                                null comment '0=待审核 1=审核通过 2=审核未通过'
);

