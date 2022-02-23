create table quest_tag
(
    quest_id integer not null
        constraint quest_tag_quest_id_fk
            references quest
            on delete cascade,
    tag_id   integer not null
        constraint quest_tag_tag_id_fk
            references tag
            on delete cascade,
    constraint quest_tag_pk
        primary key (tag_id, quest_id)
);