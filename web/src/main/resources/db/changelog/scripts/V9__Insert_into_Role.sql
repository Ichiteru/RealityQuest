insert into role (name) values ('ROLE_GUEST');
insert into role (name) values ('ROLE_USER');
insert into role (name) values ('ROLE_OWNER');

insert into usr (id, name, surname, email, username) values (10, 'Ilya', 'Charniauski', 'chern200213@gmail.com', 'ichiteru');
insert into user_role values (10, 1);
insert into user_role values (10, 2);
insert into user_role values (10, 3);