

create table developers(
	id bigint primary key,
	first_name varchar(15) not null,
	age int,
	gender varchar(10),
	salary int
);

create table skills(
	id bigint primary key,
	name_skill varchar(15),
	level_skill varchar(15)
);

create table projects(
	id bigint primary key,
	name_project varchar(20),
	deadline varchar(30)
);

create table companies(
	id bigint primary key,
	name_company varchar(30)
);

create table customers(
	id bigint primary key,
	first_name varchar(15)
);