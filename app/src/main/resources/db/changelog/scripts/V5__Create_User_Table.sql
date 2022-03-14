create table usr
(
    id bigserial not null
        constraint usr_pk
            primary key,
    name     varchar not null,
    surname  varchar not null,
    email    varchar not null,
    username varchar not null
);

create unique index usr_email_uindex
    on usr (email);

create unique index usr_id_uindex
    on usr (id);

create unique index usr_name_surname_uindex
    on usr (name, surname);

create unique index username_uindex
    on usr (username);

