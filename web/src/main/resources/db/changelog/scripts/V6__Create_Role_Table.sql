create table role (
    id bigserial not null
        constraint role_pk
                  primary key,
    name varchar not null
);

create unique index role_name_uindex
    on role(name);

