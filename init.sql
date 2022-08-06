create table companies(
	id bigint primary key,
	name_company varchar(30)
);

create table customers(
	id bigint primary key,
	first_name varchar(15),
	id_company bigint,
	foreign key (id_company) references companies(id) on delete cascade
);

create table projects(
	id bigint primary key,
	name_project varchar(20),
	deadline varchar(30),
	id_company bigint,
	id_customer bigint,
	foreign key (id_company) references companies(id) on delete cascade,
	foreign key (id_customer) references customers(id) on delete cascade
);

create table developers(
	id bigint primary key,
	first_name varchar(15) not null,
	age int,
	gender varchar(10),
	salary int,
	id_project bigint,
	foreign key (id_project) references projects(id) on delete cascade
);

create table skills(
	id bigint primary key,
	name_skill varchar(15),
	level_skill varchar(15),
	id_developer bigint,
	foreign key (id_developer) references developers(id) on delete cascade
);



