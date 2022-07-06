create table developers(
	id bigint primary key,
	first_name varchar(15) not null,
	age int,
	gender varchar(10)
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

create table developer_project(
	developer_id bigint,
	progect_id bigint,
	foreign key (developer_id) references developers(id),
	foreign key (progect_id) references projects(id)
);

create table developer_skills(
	developer_id bigint,
	skill_id bigint,
	foreign key (developer_id) references developers(id),
	foreign key (skill_id) references skills(id)
);

create table company_project(
	company_id bigint,
	project_id bigint,
	foreign key (company_id) references companies(id),
	foreign key (project_id) references projects(id)
);

create table customer_project(
	customer_id bigint,
	project_id bigint,
	foreign key (project_id) references projects(id),
	foreign key (customer_id) references customers(id)
);
