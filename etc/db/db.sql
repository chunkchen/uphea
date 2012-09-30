/*
created: 2010-03-24
modified: 2011-02-23
model: uphea
database: mysql 5.1
*/

-- create tables section -------------------------------------------------

-- table up_question

create table up_question
(
  id int unsigned not null auto_increment,
  country_id int unsigned,
  date int(8) unsigned not null
  comment 'format: yyyymmdd',
  text varchar(1000) not null,
  registered_only bool not null,
 primary key (id)
) engine = innodb
;

create index ndx_question_country on up_question (country_id)
;

create index ndx_question_date on up_question (date)
;

-- table up_answer

create table up_answer
(
  id int unsigned not null auto_increment,
  question_id int unsigned not null,
  text varchar(250) not null,
  votes int unsigned not null default 0,
 primary key (id)
) engine = innodb
;

create index ndx_answer_question on up_answer (question_id)
;

-- table up_user

create table up_user
(
  id int unsigned not null auto_increment,
  level int unsigned not null,
  country_id int unsigned,
  email varchar(100) not null,
  hashpw char(60) not null,
  sex char(1),
  birth_year int(4) unsigned,
  name varchar(100),
  notice bool not null default 0,
  confirmed bool not null,
  last_login timestamp not null,
  since timestamp not null,
 primary key (id)
) engine = innodb
;

-- table up_vote

create table up_vote
(
  id int unsigned not null auto_increment,
  answer_id int unsigned not null,
  user_id int unsigned,
  ip_address varchar(40) not null,
  time timestamp not null,
 primary key (id)
) engine = innodb
;

create index ndx_vote_answer on up_vote (answer_id)
;

create index ndx_vote_user on up_vote (user_id)
;

-- table up_country

create table up_country
(
  id int unsigned not null
  comment 'equals to country numeric code',
  name varchar(100) not null,
  alpha2 char(2) not null,
  alpha3 char(3) not null,
  active bool not null
) engine = innodb
;

alter table up_country add primary key (id)
;

-- table up_user_level

create table up_user_level
(
  id int unsigned not null,
  name char(20) not null
) engine = innodb
;

alter table up_user_level add primary key (id)
;

-- table up_favorites

create table up_favorites
(
  id int unsigned not null auto_increment,
  question_id int unsigned not null,
  user_id int unsigned not null,
 primary key (id)
) engine = innodb
;

-- table up_email_message

create table up_email_message
(
  id int unsigned not null auto_increment,
  template int unsigned not null,
  source varchar(100) not null,
  destination varchar(100) not null,
  subject varchar(500) not null,
  content mediumtext not null,
  repeat_count int unsigned not null,
  created timestamp not null,
 primary key (id)
) engine = innodb
;

-- table up_user_uid

create table up_user_uid
(
  id int unsigned not null,
  uid char(10) not null
) engine = innodb
;

alter table up_user_uid add primary key (id)
;

-- create views section -------------------------------------------------

create view up_answer_check
as
select a.id, a.votes, (select count(1) from up_vote v where v.answer_id = a.id) as votes_count from up_answer a
;

-- create relationships section ------------------------------------------------- 

alter table up_answer add constraint question_answers foreign key (question_id) references up_question (id) on delete no action on update no action
;

alter table up_vote add constraint answer_votes foreign key (answer_id) references up_answer (id) on delete no action on update no action
;

alter table up_question add constraint question_country foreign key (country_id) references up_country (id) on delete no action on update no action
;

alter table up_user add constraint user_country foreign key (country_id) references up_country (id) on delete no action on update no action
;

alter table up_user add constraint user_level foreign key (level) references up_user_level (id) on delete no action on update no action
;

alter table up_vote add constraint user_vote foreign key (user_id) references up_user (id) on delete no action on update no action
;

alter table up_favorites add constraint favorite_question foreign key (question_id) references up_question (id) on delete no action on update no action
;

alter table up_favorites add constraint user_favorite foreign key (user_id) references up_user (id) on delete no action on update no action
;

alter table up_user_uid add constraint uid foreign key (id) references up_user (id) on delete no action on update no action
;


