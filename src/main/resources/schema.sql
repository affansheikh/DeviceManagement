create table devices (
    id bigint auto_increment primary key,
    name varchar(255) not null,
    brand varchar(255) not null,
    created_at timestamp not null default NOW(),
    updated_at timestamp not null default NOW(),

    INDEX (brand)
);
