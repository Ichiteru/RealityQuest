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