--
-- deletes all votes and statistics
--

update up_answer set votes=0;
delete from up_vote;