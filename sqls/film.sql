drop table film;
create table film(
	film_id int not null auto_increment,
    film_name varchar(100) not null,
    film_abstract varchar(1000) not null,
    primary key(film_id)
);