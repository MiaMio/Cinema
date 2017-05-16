
create table timetable(
	time_id int not null auto_increment,
    time_value varchar(20) not null,
    time_film varchar(30) not null,
    seats int not null,
    cinema varchar(30) not null,
    time_film_price int not null,
    primary key(time_id)
);

