create table branch
(
    branch_id   bigserial
        primary key,
    branch_name varchar(255)
);

create table region
(
    region_id   bigserial primary key,
    branch_id   bigint references branch,
    region_name varchar(255)
);


create table estimate
(
    date_from     timestamp(6) with time zone,
    date_to       timestamp(6) with time zone,
    estimate_id   bigserial
        primary key,
    region_id     bigint
        references region,
    contract_name varchar(255)
);

create table group_table
(
    estimate_id bigint
        references estimate,
    group_id    bigserial
        primary key,
    group_name  varchar(255)
);

create table job
(
    cost_estimate double precision default 1,
    quantity      double precision,
    group_id      bigint
        references group_table,
    job_id        bigserial
        primary key,
    description   varchar(255),
    sst           varchar(255),
    sub_type      varchar(255),
    unit          varchar(255)
);

create table users
(
    id       bigserial
        primary key,
    password varchar(255),
    username varchar(255)
);
