
-- create tables section -------------------------------------------------

-- table up_question

CREATE TABLE up_question
(
  id INT IDENTITY,
  country_id INT,
  date INT NOT NULL,
  text VARCHAR(1000) NOT NULL,
  registered_only BOOLEAN NOT NULL
);

CREATE INDEX ndx_question_country ON up_question (country_id)
;

CREATE INDEX ndx_question_date ON up_question (date)
;

-- table up_answer

CREATE TABLE up_answer
(
  id INT IDENTITY,
  question_id INT NOT NULL,
  text VARCHAR(250) NOT NULL,
  votes INT DEFAULT 0 NOT NULL
)
;

CREATE INDEX ndx_answer_question ON up_answer (question_id)
;

-- table up_user

CREATE TABLE up_user
(
  id INT IDENTITY,
  level INT NOT NULL,
  country_id INT,
  email VARCHAR(100) NOT NULL,
  hashpw char(60) NOT NULL,
  sex char(1),
  birth_year INT,
  name VARCHAR(100),
  notice BOOLEAN DEFAULT 0 NOT NULL,
  confirmed BOOLEAN NOT NULL,
  last_login TIMESTAMP NOT NULL,
  since TIMESTAMP NOT NULL
)
;

-- table up_vote

CREATE TABLE up_vote
(
  id INT IDENTITY,
  answer_id INT NOT NULL,
  user_id INT,
  ip_address VARCHAR(40) NOT NULL,
  time TIMESTAMP NOT NULL
)
;

CREATE INDEX ndx_vote_answer ON up_vote (answer_id)
;

CREATE INDEX ndx_vote_user ON up_vote (user_id)
;

-- table up_country

CREATE TABLE up_country
(
  id INT UNIQUE NOT NULL,
  name VARCHAR(100) NOT NULL,
  alpha2 char(2) NOT NULL,
  alpha3 char(3) NOT NULL,
  active BOOLEAN NOT NULL
)
;

-- table up_user_level

CREATE TABLE up_user_level
(
  id INT UNIQUE NOT NULL,
  name char(20) NOT NULL
)
;

-- table up_favorites

CREATE TABLE up_favorites
(
  id INT IDENTITY,
  question_id INT NOT NULL,
  user_id INT NOT NULL
)
;

-- table up_email_message

CREATE TABLE up_email_message
(
  id INT IDENTITY,
  template INT NOT NULL,
  source VARCHAR(100) NOT NULL,
  destination VARCHAR(100) NOT NULL,
  subject VARCHAR(500) NOT NULL,
  content VARCHAR(1024) NOT NULL,
  repeat_count INT NOT NULL,
  created TIMESTAMP NOT NULL
)
;

-- table up_user_uid

CREATE TABLE up_user_uid
(
  id INT IDENTITY,
  uid char(10) NOT NULL
)
;

-- create views section -------------------------------------------------

CREATE VIEW up_answer_check
AS
select a.id, a.votes, (select count(1) from up_vote v where v.answer_id = a.id) as votes_count from up_answer a
;

-- create relationships section ------------------------------------------------- 

ALTER TABLE up_answer ADD CONSTRAINT question_answers FOREIGN KEY (question_id) REFERENCES up_question (id)
;

ALTER TABLE up_vote ADD CONSTRAINT answer_votes FOREIGN KEY (answer_id) REFERENCES up_answer (id)
;

ALTER TABLE up_question ADD CONSTRAINT question_country FOREIGN KEY (country_id) REFERENCES up_country (id)
;

ALTER TABLE up_user ADD CONSTRAINT user_country FOREIGN KEY (country_id) REFERENCES up_country (id)
;

ALTER TABLE up_user ADD CONSTRAINT user_level FOREIGN KEY (level) REFERENCES up_user_level (id)
;

ALTER TABLE up_vote ADD CONSTRAINT user_vote FOREIGN KEY (user_id) REFERENCES up_user (id)
;

ALTER TABLE up_favorites ADD CONSTRAINT favorite_question FOREIGN KEY (question_id) REFERENCES up_question (id)
;

ALTER TABLE up_favorites ADD CONSTRAINT user_favorite FOREIGN KEY (user_id) REFERENCES up_user (id)
;

ALTER TABLE up_user_uid ADD CONSTRAINT uid FOREIGN KEY (id) REFERENCES up_user (id)
;



-- initial data -----------------------------------------------------------------

-- user levels

INSERT INTO up_user_level(id, name) VALUES(1, 'user')
;

INSERT INTO up_user_level(id, name) VALUES(100, 'admin')
;

-- users
INSERT INTO up_user(id, level, hashpw, email, since, last_login, confirmed)
		VALUES(1, 100,  '$2a$12$D2Z1B1gJ4j1ozxM928FZMOKTT1O2sc4aFTKlvQvnQVZkO3ShqkKSm', 'admin', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, true)
;

INSERT INTO up_user(id, level, hashpw, email, since, last_login, confirmed, name)
		VALUES(2, 1,  'xxx', 'john@uphea.com', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, true, 'John Doe')
;
INSERT INTO up_user(id, level, hashpw, email, since, last_login, confirmed, name)
		VALUES(3, 1,  'xxx', 'liz@uphea.com', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, true, 'Liz Lemon')
;
INSERT INTO up_user(id, level, hashpw, email, since, last_login, confirmed, name)
		VALUES(4, 1,  'xxx', 'tj@uphea.com', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, true, 'Tracy Jordan')
;
INSERT INTO up_user(id, level, hashpw, email, since, last_login, confirmed, name)
		VALUES(5, 1,  'xxx', 'jane@jodd.org', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, true, 'Just Jane')
;
INSERT INTO up_user(id, level, hashpw, email, since, last_login, confirmed, name)
		VALUES(6, 1,  'xxx', 'luk@sw.com', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, true, 'luke skywalker')
;
INSERT INTO up_user(id, level, hashpw, email, since, last_login, confirmed, name)
		VALUES(7, 1,  'xxx', 'anakhin@sw.com', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, true, 'Darth Vader')
;
INSERT INTO up_user(id, level, hashpw, email, since, last_login, confirmed, name)
		VALUES(8, 1,  'xxx', 'indy@tomb.com', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, true, 'Indiana Jones jr.')
;
INSERT INTO up_user(id, level, hashpw, email, since, last_login, confirmed, name)
		VALUES(9, 1,  'xxx', 'bat@jodd.org', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, true, 'Batman')
;
INSERT INTO up_user(id, level, hashpw, email, since, last_login, confirmed, name)
		VALUES(10, 1,  'xxx', 'joker@jodd.org', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, true, 'Joker')
;
INSERT INTO up_user(id, level, hashpw, email, since, last_login, confirmed, name)
		VALUES(11, 1,  'xxx', 'green@lantern.com', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, true, 'Green Lantern')
;
INSERT INTO up_user(id, level, hashpw, email, since, last_login, confirmed, name)
		VALUES(12, 1,  'xxx', 'shelly@tbbt.com', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, true, 'Dr. Sheldon Cooper')
;
INSERT INTO up_user(id, level, hashpw, email, since, last_login, confirmed, name)
		VALUES(13, 1,  'xxx', 'leo@tbbt.com', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, true, 'Leonard Hofstadter')
;
INSERT INTO up_user(id, level, hashpw, email, since, last_login, confirmed, name)
		VALUES(14, 1,  'xxx', 'lovetoy@tbbt.com', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, true, 'Howard Wolowitz')
;
INSERT INTO up_user(id, level, hashpw, email, since, last_login, confirmed, name)
		VALUES(15, 1,  'xxx', 'raj@tbbt.com', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, true, 'Raj Koothrappali')
;
INSERT INTO up_user(id, level, hashpw, email, since, last_login, confirmed, name)
		VALUES(16, 1,  'xxx', 'comics@tbbt.com', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, true, 'Stuart')
;
INSERT INTO up_user(id, level, hashpw, email, since, last_login, confirmed, name)
		VALUES(17, 1,  'xxx', 'bern@tbbt.com', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, true, 'Bernadette Rostenkowski')
;
INSERT INTO up_user(id, level, hashpw, email, since, last_login, confirmed, name)
		VALUES(18, 1,  'xxx', 'aff@tbbt.com', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, true, 'Amy Farrah Fowler')
;
INSERT INTO up_user(id, level, hashpw, email, since, last_login, confirmed, name)
		VALUES(19, 1,  'xxx', 'fla@sh.com', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, true, 'Flash')
;
INSERT INTO up_user(id, level, hashpw, email, since, last_login, confirmed, name)
		VALUES(20, 1,  'xxx', 'noone@no.com', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, true, 'NoOne')
;
INSERT INTO up_user(id, level, hashpw, email, since, last_login, confirmed, name)
		VALUES(21, 1,  'xxx', 'corto@venice.com', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, true, 'Corto Maltese')
;
INSERT INTO up_user(id, level, hashpw, email, since, last_login, confirmed, name)
		VALUES(22, 1,  'xxx', 'igor@venice.com', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, true, 'Igor')
;
INSERT INTO up_user(id, level, hashpw, email, since, last_login, confirmed, name)
		VALUES(23, 1,  'xxx', 'jodd@venice.com', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, true, 'Jodd')
;
