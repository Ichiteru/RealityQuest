create table quest
(
    id                bigserial
        primary key,
    name              varchar,
    genre             varchar,
    price             double precision,
    description       varchar,
    duration          time,
    creation_date     date,
    modification_date date,
    max_people        integer
);