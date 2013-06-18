package com.uphea.service;

import com.uphea.domain.Question;
import com.uphea.util.DateUtil;
import jodd.datetime.JDateTime;
import jodd.db.oom.DbOomQuery;
import jodd.joy.db.AppDao;
import jodd.petite.meta.PetiteBean;
import jodd.petite.meta.PetiteInject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static jodd.db.oom.DbOomQuery.query;
import static jodd.db.oom.sqlgen.DbSqlBuilder.sql;

/**
 * Questions services.
 */
@PetiteBean
public class QuestionService {

	private static final Logger log = LoggerFactory.getLogger(QuestionService.class);

	/**
	 * App Dao simplifies database usage a lot.
	 */
	@PetiteInject
	AppDao appDao;

	// ---------------------------------------------------------------- questions

	/**
	 * Finds question by id.
	 */
	public Question findQuestionById(Long id) {
		return appDao.findById(Question.class, id);
	}

	/**
	 * Finds default question for provided date.
	 * Default question has no associated country.
	 * Returns <code>null</code> if no question is found at all.
	 */
	public Question findQuestionForDate(JDateTime jd) {
		return findQuestionForDate(DateUtil.toIntDate(jd));
	}

	public Question findQuestionForDate(int date) {
		if (log.isDebugEnabled()) {
			log.debug("finding question for " + date);
		}
		DbOomQuery dbOom = query(sql("select $C{q.*} from $T{Question q} where $q.countryId is null and $q.date <= :date order by $q.date desc limit 0,1"));
		dbOom.setMaxRows(1);
		dbOom.setFetchSize(1);
		dbOom.setInteger("date", date);
		return dbOom.findAndClose(Question.class);
	}

	/**
	 * Finds next question of current one.
	 * Returns <code>null</code> if there is no next question.
	 */
	public Question findNextQuestion(Question q) {
		DbOomQuery dbOom = query(sql("select $C{q.*} from $T{Question q} where $q.date > :date order by $q.date limit 0,1"));
		dbOom.setMaxRows(1);
		dbOom.setFetchSize(1);
		dbOom.setInteger("date", q.getDate());
		return dbOom.findAndClose(Question.class);
	}

	/**
	 * Finds previous question of current one.
	 * Returns <code>null</code> if there is no previous question.
	 */
	public Question findPreviousQuestion(Question q) {
		DbOomQuery dbOom = query(sql("select $C{q.*} from $T{Question q} where $q.date < :date order by $q.date desc limit 0,1"));
		dbOom.setMaxRows(1);
		dbOom.setFetchSize(1);
		dbOom.setInteger("date", q.getDate());
		return dbOom.findAndClose(Question.class);
	}

	/**
	 * Returns list of previous questions.
	 */
	public List<Question> findPreviousQuestions(Question q, int howMany) {
		DbOomQuery dbOom = query(sql("select $C{q.*} from $T{Question q} where $q.date < :date order by $q.date desc limit 0,:howMany"));
		dbOom.setInteger("date", q.getDate());
		dbOom.setInteger("howMany", howMany);
		return dbOom.listAndClose(Question.class);
	}

	/**
	 * Finds random question from the past.
	 */
	public Question findRandomPastQuestion(JDateTime untilDate) {
		DbOomQuery dbOom = query(sql("select $C{q.*} from $T{Question q} where $q.date < :date order by rand() limit 1"));
		dbOom.setMaxRows(1);
		dbOom.setFetchSize(1);	
		dbOom.setInteger("date", DateUtil.toIntDate(untilDate));
		return dbOom.findAndClose(Question.class);
	}

	/**
	 * Finds question for given answer.
	 */
	public Question findAnswersQuestion(Long answerId) {
		DbOomQuery dbOom = query(sql("select $C{q.*} from $T{Question q} join $T{Answer a} on $q.id=$a.questionId where $a.id=:answerId"));
		dbOom.setLong("answerId", answerId);
		return dbOom.find(Question.class);
	}

}