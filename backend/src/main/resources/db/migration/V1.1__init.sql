create table if not exists flyway_schema_history
(
    installed_rank integer                 not null
        constraint flyway_schema_history_pk
            primary key,
    version        varchar(50),
    description    varchar(200)            not null,
    type           varchar(20)             not null,
    script         varchar(1000)           not null,
    checksum       integer,
    installed_by   varchar(100)            not null,
    installed_on   timestamp default now() not null,
    execution_time integer                 not null,
    success        boolean                 not null
);

alter table flyway_schema_history
    owner to postgres;

create index if not exists flyway_schema_history_s_idx
    on flyway_schema_history (success);

create table if not exists usr
(
    id              varchar(255) not null
        constraint usr_pkey
            primary key,
    activation_code varchar(255),
    active          boolean default true,
    email           varchar(255),
    last_visit      timestamp,
    locale          varchar(255),
    password        varchar(255),
    picture         varchar(255),
    username        varchar(255)
);

alter table usr
    owner to postgres;

create table if not exists post
(
    id               bigint not null
        constraint post_pkey
            primary key,
    author           bytea,
    creation_date    varchar(255),
    message          varchar(255),
    pic              varchar(255),
    tags             varchar(255),
    link             varchar(255),
    link_cover       varchar(255),
    link_description varchar(255),
    link_title       varchar(255),
    text             varchar(255),
    user_id          varchar(255)
        constraint fkrm2u0ujvvi9euawhsm1km29m4
            references usr
);

alter table post
    owner to postgres;

create table if not exists user_roles
(
    user_id varchar(255) not null
        constraint fkg6agnwreityp2vf23bm2jgjm3
            references usr,
    roles   varchar(255)
);

alter table user_roles
    owner to postgres;

create table if not exists comment
(
    id      bigint       not null
        constraint comment_pkey
            primary key,
    text    varchar(255),
    user_id varchar(255) not null
        constraint fkgcgdcgly6u49hf4g8y2di3g4p
            references usr,
    post_id bigint
        constraint fks1slvnkuemjsq2kj4h3vhx7i1
            references post
);

alter table comment
    owner to postgres;

create table if not exists user_subscription
(
    active        boolean      not null,
    channel_id    varchar(255) not null
        constraint fkhyluo30h2xayow5x0qrgd8o47
            references usr,
    subscriber_id varchar(255) not null
        constraint fklpe4u2ktgt5qtjnqa4wre8j2b
            references usr,
    constraint user_subscription_pkey
        primary key (channel_id, subscriber_id)
);

alter table user_subscription
    owner to postgres;

insert into usr(id, email, password, picture, username)
values ('9ac0d7136bdc983a016bdc9867710000', null,
        '$2a$10$pvNnRpXBIy2btZaSumhmrOdniqziIFOD16b6YTEST6TwUOMNMNC.K', null,
        'user'),
       ('9ac0d7136bdc983a016bdc9868ee0001', null,
        '$2a$10$FiuOeU7MuH2Uh43OigcTdOWQaET/oc0/fhEiE/7ijeA/2oSu44Rma', null,
        'admin'),
        ('103451897276323278921', 'jokere3368@gmail.com', null, 'https://lh3.googleusercontent.com/a-/AAuE7mBH8wMbs8Es7E9ucN5u2wxkUiBiKPE0h6aU5xsSeA', 'Влад Марченко')
ON CONFLICT DO NOTHING;

insert into user_roles
values ('9ac0d7136bdc983a016bdc9868ee0001', 'USER'),
       ('9ac0d7136bdc983a016bdc9867710000', 'USER'),
       ('9ac0d7136bdc983a016bdc9868ee0001', 'ADMIN')
ON CONFLICT DO NOTHING;

insert into user_subscription
values (true, '9ac0d7136bdc983a016bdc9868ee0001', '9ac0d7136bdc983a016bdc9867710000'),
       (true, '9ac0d7136bdc983a016bdc9867710000', '9ac0d7136bdc983a016bdc9868ee0001'),
       (true, '103451897276323278921', '9ac0d7136bdc983a016bdc9867710000'),
       (true, '103451897276323278921', '9ac0d7136bdc983a016bdc9868ee0001')
ON CONFLICT DO NOTHING;

insert into post(id, creation_date, link, link_cover, text, user_id)
values (125, '2019-09-04 12:30:33.88+03', 'https://youtu.be/7PIMiDcwNvc', null, 'https://youtu.be/7PIMiDcwNvc', '103451897276323278921'),
       (128, '2019-09-04 12:32:40.566+03', 'https://d1q6f0aelx0por.cloudfront.net/product-logos/5431a80b-9ab9-486c-906a-e3d4b5ccaa96-hello-world.png', 'https://d1q6f0aelx0por.cloudfront.net/product-logos/5431a80b-9ab9-486c-906a-e3d4b5ccaa96-hello-world.png', 'Hello there! You can add image or video from youtube just by copying link here: https://d1q6f0aelx0por.cloudfront.net/product-logos/5431a80b-9ab9-486c-906a-e3d4b5ccaa96-hello-world.png', '103451897276323278921')
ON CONFLICT DO NOTHING;

insert into comment
values (129, 'yeah, right.', '9ac0d7136bdc983a016bdc9867710000', 128)
ON CONFLICT DO NOTHING;

