
create table comments(
	comment_id int not null auto_increment,
    comment_film varchar(30) not null,
    comment_customer varchar(20) not null,
    comment_content varchar(1000),
    comment_time varchar(100) not null,
    comment_score int not null,
    primary key(comment_id)
);