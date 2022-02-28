create table quest_user
(
    user_id  bigint not null
        constraint quest_user_user_id_fk
            references usr
            on delete cascade,
    quest_id bigint not null
        constraint quest_user_quest_id_fk
            references quest
            on delete cascade,
    constraint quest_user_pk
        primary key (user_id, quest_id),
    order_cost money,
    purchase_time timestamp,
    reserve_time time
)