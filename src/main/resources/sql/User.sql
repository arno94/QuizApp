create table User
(
    id       int not null primary key,
    date     datetime  null,
    password varchar(255) null,
    username varchar(255) null,
    roles varchar(255) null,
    active   bit not null
);

-- Add roles column
alter table User
    add column roles varchar(255) null;

alter table User
    add column active bit not null default true;

