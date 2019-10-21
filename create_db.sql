create table user
(
	id int auto_increment,
	account_id varchar(100),
	token char(36),
	name varchar(100),
	gmt_create bigint,
	gmt_modified bigint,
	primary key (id)
);