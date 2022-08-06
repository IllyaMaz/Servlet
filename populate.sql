insert into companies(id,name_company)
values (1,'Global'),
(2,'Luxoft'),
(3,'Epam');

insert into customers (id,first_name,id_company)
values (1,'Adam',1),
(2,'John',2),
(3,'Jane',3);

insert into projects(id,name_project,deadline,id_company,id_customer)
values (1,'site','20 april 2020',1,1),
(2,'application','14 may 2021',2,2),
(3,'OS','26 september 2020',3,3);

insert into developers (id,first_name,age,gender,salary,id_project)
values (1,'Paule',20,'male',20000,1),
(2,'Egor',21,'male',25000,1),
(3,'Anton',22,'male',30000,2),
(4,'Illya',23,'male',35000,3);

insert into skills(id,name_skill,level_skill,id_developer)
values(1,'Java','Junior',1),
(2,'PHP','Midle',2),
(3,'C++','Senior',3),
(4,'Java','Junior',2);





