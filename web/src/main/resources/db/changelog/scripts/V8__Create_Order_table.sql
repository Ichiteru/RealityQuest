create table quest_user
(
    id                bigserial
        primary key,
    user_id  bigint not null
        constraint quest_user_user_id_fk
            references usr
            on delete cascade,
    quest_id bigint not null
        constraint quest_user_quest_id_fk
            references quest
            on delete cascade,
    order_cost double precision,
    purchase_time timestamp,
    reserve_time timestamp,
    end_time timestamp
)