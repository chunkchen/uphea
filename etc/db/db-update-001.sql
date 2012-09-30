
--
-- delete up.question.ref column
--

alter table up_question DROP FOREIGN KEY question_ref;

alter table up_question DROP ref;


--
-- add email
--

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
