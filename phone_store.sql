create table buyer_address
(
    address_id    int auto_increment
        primary key,
    buyer_name    varchar(32)                         not null comment '买家名字',
    buyer_phone   varchar(32)                         not null comment '买家电话',
    buyer_address varchar(128)                        not null comment '买家地址',
    area_code     varchar(32)                         null comment '地址编码',
    update_time   timestamp default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '修改时间',
    create_time   timestamp default CURRENT_TIMESTAMP not null comment '创建时间'
)
    comment '收货地址表' collate = utf8mb4_general_ci;

create table order_master
(
    order_id       varchar(32)                          not null
        primary key,
    buyer_name     varchar(32)                          not null comment '买家名字',
    buyer_phone    varchar(32)                          not null comment '买家电话',
    buyer_address  varchar(128)                         not null comment '买家地址',
    phone_id       int                                  null comment '商品编号',
    phone_name     varchar(32)                          null comment '商品名称',
    phone_quantity int                                  null comment '商品数量',
    phone_icon     varchar(512)                         null comment '商品小图',
    specs_id       int                                  null comment '规格编号',
    specs_name     varchar(32)                          null comment '规格名称',
    specs_price    decimal(8, 2)                        null comment '规格单价',
    order_amount   decimal(8, 2)                        not null comment '订单总金额',
    pay_status     tinyint(3) default 0                 not null comment '支付状态，默认0未支付',
    create_time    timestamp  default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time    timestamp  default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '修改时间'
)
    comment '订单表' collate = utf8mb4_general_ci;

create table phone_category
(
    category_id   int auto_increment
        primary key,
    category_name varchar(64)                         not null comment '类目名称',
    category_type int                                 not null comment '类目编号',
    create_time   timestamp default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time   timestamp default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '修改时间',
    constraint uqe_category_type
        unique (category_type)
)
    comment '类目表' collate = utf8mb4_general_ci;

create table phone_info
(
    phone_id          int auto_increment
        primary key,
    phone_name        varchar(64)                         not null comment '商品名称',
    phone_price       decimal(12, 2)                      not null comment '商品单价',
    phone_description varchar(64)                         null comment '描述',
    phone_stock       int                                 not null comment '库存',
    phone_icon        varchar(512)                        null comment '小图',
    category_type     int                                 not null comment '类目编号',
    create_time       timestamp default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time       timestamp default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '修改时间',
    phone_tag         varchar(512)                        null comment '标签'
)
    comment '商品表' collate = utf8mb4_general_ci;

create table phone_specs
(
    specs_id      int auto_increment
        primary key,
    phone_id      varchar(32)                         not null,
    specs_name    varchar(64)                         not null comment '规格名称',
    specs_stock   int                                 not null comment '库存',
    specs_price   decimal(12, 2)                      not null comment '单价',
    specs_icon    varchar(512)                        null comment '小图',
    specs_preview varchar(512)                        null comment '预览图',
    update_time   timestamp default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '修改时间',
    create_time   timestamp default CURRENT_TIMESTAMP not null comment '创建时间'
)
    comment '商品规格表' collate = utf8mb4_general_ci;


