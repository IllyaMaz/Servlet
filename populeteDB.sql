insert into developers (id,first_name,age,gender)
values (1,'Paule',20,'male'),
(2,'Egor',21,'male'),
(3,'Anton',22,'male'),
(4,'Illya',23,'male');

insert into skills(id,name_skill,level_skill)
values(1,'Java','Junior'),
(2,'PHP','Midle'),
(3,'C++','Senior');


insert into developer_skills(developer_id,skill_id)
values(1,1),
(2,2),
(3,1),
(1,2);

insert into projects(id,name_project,deadline)
values (1,'site','20 april 2020'),
(2,'application','14 may 2021'),
(3,'OS','26 september 2020');

insert into companies(id,name_company)
values (1,'Global'),
(2,'Luxoft'),
(3,'Epam');

insert into customers (id,first_name)
values (1,'Adam'),
(2,'John'),
(3,'Jane');

insert into developer_project(developer_id,progect_id)
values (1,1),
(2,1),
(3,1),
(2,2),
(3,2),
(1,3),
(3,3);

insert into company_project(company_id,project_id)
values (1,1),
(2,2),
(3,3);

insert into customer_project(customer_id,project_id)
values (1,1),
(2,2),
(3,3);