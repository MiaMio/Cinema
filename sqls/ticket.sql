
create table ticket(
	ticket_id int not null auto_increment,
    ticket_film varchar(20) not null,
    ticket_value int not null,
    ticket_customer varchar(20) not null,
    ticket_time varchar(20) not null,
    ticket_cinema varchar(20) not null,
    primary key(ticket_id)
);