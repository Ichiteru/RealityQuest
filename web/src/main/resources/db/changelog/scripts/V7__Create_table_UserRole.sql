create table user_role
(
    user_id bigint not null
        constraint user_role_user_id_fk
            references usr
            on delete cascade,
    role_id   bigint not null
        constraint user_role_role_id_fk
            references role
            on delete cascade,
    constraint user_role_pk
        primary key (user_id, role_id)
);