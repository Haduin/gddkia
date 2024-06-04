create table if not exists branch
(
    geo_coordinatex                       double precision not null,
    geo_coordinatey                       double precision not null,
    managed_network_length                double precision not null,
    branch_id                             bigserial
        primary key,
    address_street                        varchar(255),
    address_zip_city                      varchar(255),
    branch                                varchar(255),
    city                                  varchar(255),
    notes                                 varchar(255),
    phone                                 varchar(255),
    region                                varchar(255),
    road_number                           varchar(255),
    road_section_at_district_headquarters varchar(255),
    section                               varchar(255)
);

create table if not exists estimate
(
    date_from     timestamp(6) with time zone,
    date_to       timestamp(6) with time zone,
    estimate_id   bigserial
        primary key,
    road_length   bigint,
    contract_name varchar(255)
);

create table if not exists branch_estimate
(
    branch_id   bigint not null
        references branch,
    estimate_id bigint not null
        references estimate
);

create table if not exists group_table
(
    estimate_id bigint
        references estimate,
    group_id    bigserial
        primary key,
    group_name  varchar(255)
);

create table if not exists jobs
(
    cost_estimate double precision default 1,
    quantity      double precision,
    group_id      bigint            references group_table,
    job_id        bigserial
        primary key,
    description   varchar(255),
    sst           varchar(255),
    sub_type      varchar(255),
    unit          varchar(255)
);

create table if not exists users
(
    id       bigserial
        primary key,
    password varchar(255),
    username varchar(255)
);
