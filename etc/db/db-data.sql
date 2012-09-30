
-- user levels
insert into up_user_level(id, name) value(1, 'user');
insert into up_user_level(id, name) value(100, 'admin');

-- user
insert into up_user(id, level, hashpw, email, since, last_login)
	value(1, 100,  '$2a$12$D2Z1B1gJ4j1ozxM928FZMOKTT1O2sc4aFTKlvQvnQVZkO3ShqkKSm', 'admin', current_timestamp, current_timestamp);


-- initial question
insert into up_question(id, date, text, registered_only)
	value(1, 20100401, 'Do you like Jodd?', false);

insert into up_answer(id, question_id, text) value(1, 1, 'Yes');
insert into up_answer(id, question_id, text) value(2, 1, 'No');

