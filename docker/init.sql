-- create database test;

create table quest
(
    id                serial
        primary key,
    name              varchar,
    genre             varchar,
    price             money,
    description       varchar,
    duration          time,
    creation_date     date,
    modification_date date,
    max_people        integer
);

create table tag
(
    id   serial
        primary key,
    name varchar
);

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

insert into quest
values (10, 'test', 'test', 12.34, 'test', '03:00:00', '2022-02-18', '2022-02-18', 10);
insert into quest
values (12, 'test22', 'test2', 12.34, 'test2', '02:40:40', '2022-02-18', '2022-02-18', 12);
insert into quest
values (14, 'test3', 'test3', 12.34, 'test3', '03:50:34', '2022-02-18', '2022-02-18', 14);

insert into tag values (1, 'horror');
insert into tag values (2, 'adventure');
insert into tag values (3, 'for children');
insert into tag values (4, '+18');
insert into tag values (5, '+12');
insert into tag values (6, 'detective');
insert into tag values (7, 'love story');

insert into quest_tag values (10, 1);
insert into quest_tag values (10, 3);
insert into quest_tag values (10, 7);
insert into quest_tag values (12, 5);
insert into quest_tag values (12, 7);
insert into quest_tag values (12, 4);
insert into quest_tag values (14, 2);
insert into quest_tag values (14, 3);
insert into quest_tag values (14, 7);
insert into quest_tag values (14, 6);

CREATE OR REPLACE FUNCTION searchByNameAndDescription(pname varchar(20), pdescription varchar(20))
    RETURNS TABLE (
                      id int, name varchar, genre varchar, price money, duration time, max_people int
                  )
    LANGUAGE plpgsql AS
$func$
BEGIN
    RETURN QUERY
        SELECT q.id, q.name, q.genre, q.price, q.duration,  q.max_people from quest as q
        where upper(q.name) LIKE '%' || upper(pname) || '%' and upper(q.description) LIKE '%' || upper(pdescription) || '%';
END
$func$;