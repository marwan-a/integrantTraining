create table IF NOT EXISTS users(
user_id int NOT NULL AUTO_INCREMENT PRIMARY KEY,
name varchar(60) not null,
email varchar(60) not null unique,
password varchar(200) not null,
enabled boolean not null default false
);
create table IF NOT EXISTS roles(
role_id int NOT NULL AUTO_INCREMENT PRIMARY KEY,
name varchar(60) not null unique
);
create table IF NOT EXISTS privilege(
privilege_id int NOT NULL AUTO_INCREMENT PRIMARY KEY,
name varchar(60) not null unique
);
create table IF NOT EXISTS users_roles(
user_id int,
role_id int,
primary key (user_id,role_id),
foreign key (user_id) references users(user_id) on delete cascade on update cascade,
foreign key (role_id) references roles(role_id) on delete cascade on update cascade
);
create table IF NOT EXISTS roles_privileges(
role_id int,
privilege_id int,
primary key (privilege_id,role_id),
foreign key (role_id) references roles(role_id) on delete cascade on update cascade,
foreign key (privilege_id) references privilege(privilege_id) on delete cascade on update cascade
);
create table IF NOT EXISTS confirmationtoken(
token_id int NOT NULL AUTO_INCREMENT PRIMARY KEY,
confirmation_token varchar(200),
createdDate datetime,
expiryDate datetime,
user_id int,
foreign key(user_id) references users(user_id) on delete cascade on update cascade
);
create table IF NOT EXISTS messages(
id int NOT NULL AUTO_INCREMENT PRIMARY KEY,
content varchar(200) not null
);