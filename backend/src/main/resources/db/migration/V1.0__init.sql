create schema if not exists public;

comment on schema public is 'Artifact`s schema';

alter schema public owner to postgres;

create table if not exists flyway_schema_history
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

create index if not exists flyway_schema_history_s_idx
    on flyway_schema_history (success);

create table if not exists permission
(
    id varchar(255) not null
        constraint permission_pkey
            primary key,
    name varchar(255)
);

alter table permission owner to postgres;

create table if not exists role
(
    id varchar(255) not null
        constraint role_pkey
            primary key,
    name varchar(255)
);

alter table role owner to postgres;

create table if not exists permission_role
(
    role_id varchar(255) not null
        constraint fk50sfdcvbvdaclpn7wp4uop4ml
            references role,
    permission_id varchar(255) not null
        constraint fk3tuvkbyi6wcytyg21hvpd6txw
            references permission
);

alter table permission_role owner to postgres;

create table if not exists usr
(
    id varchar(255) not null
        constraint usr_pkey
            primary key,
    activation_code varchar(255),
    active boolean default true,
    email varchar(255),
    last_visit timestamp,
    lastname varchar(255),
    locale varchar(255),
    name varchar(255),
    password varchar(255),
    picture varchar(255),
    username varchar(255)
);

alter table usr owner to postgres;

create table if not exists post
(
    id varchar(255) not null
        constraint post_pkey
            primary key,
    creation_date varchar(255),
    message varchar(255),
    pic varchar(255),
    tags varchar(255),
    user_id varchar(255)
        constraint fkrm2u0ujvvi9euawhsm1km29m4
            references usr
);

alter table post owner to postgres;

create table if not exists role_user
(
    user_id varchar(255) not null
        constraint fk9m0k8ofl4n2iamt5jc4r26050
            references usr,
    role_id varchar(255) not null
        constraint fkiqpmjd2qb4rdkej916ymonic6
            references role
);

alter table role_user owner to postgres;

