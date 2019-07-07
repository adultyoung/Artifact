create table flyway_schema_history
(
    installed_rank integer not null
        constraint flyway_schema_history_pk
            primary key,
    version varchar(50),
    description varchar(200) not null,
    type varchar(20) not null,
    script varchar(1000) not null,
    checksum integer,
    installed_by varchar(100) not null,
    installed_on timestamp default now() not null,
    execution_time integer not null,
    success boolean not null
);

alter table flyway_schema_history owner to postgres;

create index flyway_schema_history_s_idx
    on flyway_schema_history (success);

create table permission
(
    id bigint not null
        constraint permission_pkey
            primary key,
    name varchar(255)
);

alter table permission owner to postgres;

create table post
(
    id bigint not null
        constraint post_pkey
            primary key,
    author bytea,
    creation_date varchar(255),
    message varchar(255),
    pic varchar(255),
    tags varchar(255)
);

alter table post owner to postgres;

create table role
(
    id bigint not null
        constraint role_pkey
            primary key,
    name varchar(255)
);

alter table role owner to postgres;

create table permission_role
(
    role_id bigint not null
        constraint fk50sfdcvbvdaclpn7wp4uop4ml
            references role,
    permission_id bigint not null
        constraint fk3tuvkbyi6wcytyg21hvpd6txw
            references permission
);

alter table permission_role owner to postgres;

create table usr
(
    id bigint not null
        constraint usr_pkey
            primary key,
    activation_code varchar(255),
    active boolean default true,
    email varchar(255),
    last_visit timestamp,
    locale varchar(255),
    password varchar(255),
    picture varchar(255),
    username varchar(255)
);

alter table usr owner to postgres;

create table user_roles
(
    user_id bigint not null
        constraint fkg6agnwreityp2vf23bm2jgjm3
            references usr,
    roles varchar(255)
);

alter table user_roles owner to postgres;

