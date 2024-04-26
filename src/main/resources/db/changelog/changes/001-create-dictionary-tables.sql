
CREATE TABLE dict_titles (
  id INTEGER PRIMARY KEY,
  description VARCHAR(50) NOT NULL
);

insert into dict_titles(id, description) values(1, 'Генеральный директор');
insert into dict_titles(id, description) values(2, 'Зам. генерального директора');
insert into dict_titles(id, description) values(3, 'Главный бухгалтер');
insert into dict_titles(id, description) values(4, 'Бухгалтер');
insert into dict_titles(id, description) values(5, 'Руководитель отдела продаж');
insert into dict_titles(id, description) values(6, 'Менеджер по продажам');
insert into dict_titles(id, description) values(7, 'Руководитель отдела разработки ПО');
insert into dict_titles(id, description) values(8, 'Старший разработчик ПО');
insert into dict_titles(id, description) values(9, 'Младший разработчик ПО');
