package com.uphea.service;

import com.uphea.domain.Answer;
import com.uphea.domain.Question;
import com.uphea.domain.User;
import com.uphea.domain.Vote;
import jodd.datetime.JDateTime;
import jodd.db.oom.DbOomQuery;
import jodd.db.oom.sqlgen.DbEntitySql;
import jodd.db.oom.sqlgen.DbSqlBuilder;
import jodd.joy.db.AppDao;
import jodd.petite.meta.PetiteBean;
import jodd.petite.meta.PetiteInject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static jodd.db.oom.DbOomQuery.query;
import static jodd.db.oom.sqlgen.DbSqlBuilder.sql;

/**
 * Answer and votes services.
 */
@PetiteBean
public class AnswerService {

	private static final Logger log = LoggerFactory.getLogger(AnswerService.class);

	@PetiteInject
	AppDao appDao;
	
	// ---------------------------------------------------------------- answers

	/**
	 * Loads answers into the question. Answers are ordered by entity id.
	 * <p>
	 * This is an example of using <code>DbSqlBuilder</code> and query building using
	 * chunks. Here we also use <code>DbEntitySql#findForeign</code> to
	 * generate selecting foreign entity for given parent entity.
	 * <p>
	 * However, it is recommended to use template sql instead, since it
	 * is more readable.
	 */
	public void loadAnswers(Question q) {
		DbSqlBuilder dbb = DbEntitySql.findForeign(Answer.class, q);
		dbb._(" order by ").refId("Answer");
		List<Answer> answers = query(dbb).listAndClose(Answer.class);
		q.setAnswers(answers);
	}

	/**
	 * Finds user answer for question.
	 */
	public Answer findUserAnswerForQuestion(User user, Question question) {
		DbOomQuery dbOom = query(sql("select $C{a.*} from $T{Answer a} join $T{Vote v} on $v.answerId=$a.id where $v.userId=:userId and $a.questionId=:questionId"));
		dbOom.setLong("userId", user.getId());
		dbOom.setLong("questionId", question.getId());
		return dbOom.findAndClose(Answer.class);
	}

	/**
	 * Finds answer by vote id.
	 */
	public Answer findAnswerByVoteId(Long voteId) {
		Vote vote = appDao.findById(Vote.class, voteId);
		if (vote == null) {
			return null;
		}
		return appDao.findById(Answer.class, vote.getAnswerId());
	}


	/**
	 * Updates answer votes count.
	 */
	public void updateAnswerVoteCount(Answer answer, boolean increment) {
		char operation = increment ? '+' : '-';
		DbOomQuery dbOom = query(sql("update $T{Answer a} set $a.votes = $a.votes " + operation + " 1 where $a.id = :id"));
		dbOom.setLong(1, answer.getId());
		dbOom.executeUpdateAndClose();
		if (increment) {
			answer.incrementVotes();
		} else {
			answer.decrementVotes();
		}
	}

	// ---------------------------------------------------------------- votes

	/**
	 * Finds vote by id.
	 */
	public Vote findVoteById(Long voteId) {
		return appDao.findById(Vote.class, voteId);
	}

	/**
	 * Finds user vote for question.
	 */
	public Vote findUserVoteForQuestion(User user, Question question) {
		DbOomQuery dbOom = query(sql("select $C{v.*} from $T{Vote v} join $T{Answer a} on $v.answerId=$a.id where $v.userId=:userId and $a.questionId=:questionId"));
		dbOom.setLong("userId", user.getId());
		dbOom.setLong("questionId", question.getId());
		return dbOom.findAndClose(Vote.class);
	}


	/**
	 * Votes for answer.
	 */
	public Vote voteForAnswer(Answer answer, String ipAddress, User user) {
		updateAnswerVoteCount(answer, true);
		Vote vote = new Vote();
		vote.setAnswer(answer);
		vote.setTime(new JDateTime());
		vote.setIpAddress(ipAddress);
		if (user != null) {
			vote.setUserId(user.getId());
		}
		return appDao.store(vote);
	}

	/**
	 * Updates vote for new answer.
	 */
	public Vote changeVoteAnswer(Vote currentVote, Answer currentAnswer, Answer newAnswer, String ipAddress) {
		log.debug("update vote: {}", currentVote.getId());

		updateAnswerVoteCount(currentAnswer, false);
		updateAnswerVoteCount(newAnswer, true);

		currentVote.setAnswerId(newAnswer.getId());
		currentVote.setIpAddress(ipAddress);
		return appDao.store(currentVote);
	}


	/**
	 * Counts user votes.
	 */
	public long countUserVotes(User user) {
		DbOomQuery dbOom = query(sql("select count(1) from $T{Vote v} where $v.userId = :userId"));
		dbOom.setInteger("userId", user.getId());
		return dbOom.executeCountAndClose();
	}

}
