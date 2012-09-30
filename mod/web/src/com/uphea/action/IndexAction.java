package com.uphea.action;

import com.uphea.domain.Answer;
import com.uphea.domain.Question;
import com.uphea.domain.User;
import com.uphea.domain.UserSession;
import com.uphea.service.AnswerService;
import com.uphea.service.FavoritesService;
import com.uphea.service.QuestionService;
import com.uphea.service.StatsService;
import com.uphea.util.DateUtil;
import com.uphea.worker.CookieWorker;
import jodd.datetime.JDateTime;
import jodd.joy.jtx.meta.ReadWriteTransaction;
import jodd.joy.madvoc.action.AppAction;
import jodd.joy.madvoc.meta.PostAction;
import jodd.jtx.meta.Transaction;
import jodd.madvoc.ScopeType;
import jodd.madvoc.meta.Action;
import jodd.madvoc.meta.In;
import jodd.madvoc.meta.InOut;
import jodd.madvoc.meta.MadvocAction;
import jodd.madvoc.meta.Out;
import jodd.petite.meta.PetiteInject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.Cookie;

import static jodd.joy.auth.AuthUtil.AUTH_SESSION_NAME;
import static jodd.madvoc.ScopeType.SESSION;

/**
 * Index action.
 */
@MadvocAction
public class IndexAction extends AppAction {

	private static final Logger log = LoggerFactory.getLogger(IndexAction.class);

	// ---------------------------------------------------------------- petite

	@PetiteInject
	QuestionService questionService;

	@PetiteInject
	AnswerService answerService;

	@PetiteInject
	FavoritesService favoritesService;

	@PetiteInject
	CookieWorker cookieWorker;

	@PetiteInject
	StatsService statsService;

	// ---------------------------------------------------------------- attr, params etc

	/**
	 * Cookies.
	 */
	@In(scope = ScopeType.SERVLET)
	Cookie[] cookie;

	@Out
	JDateTime now;

	@In
	Integer date;

	@Out
	JDateTime questionDate;

	@InOut
	Question question;

	@Out
	Question nextQuestion;

	@Out
	Question currentQuestion;

	@Out
	Question randomQuestion;

	@Out
	Question prevQuestion;

	@Out
	JDateTime nextQuestionDate;

	@Out
	int daysToNextQuestion;

	@Out
	Answer userAnswer;

	@Out
	Long userVoteId;

	@Out
	boolean saved;

	@Out
	boolean registeredOnlyWarn;

	@Out
	boolean isActiveQuestion;

	/**
	 * User session.
	 */
	@In(scope = SESSION, value = AUTH_SESSION_NAME)
	UserSession userSession;

	@Action("/q/${date}")
	public String viewQuestion() {
		view();
		return alias(ALIAS_INDEX_NAME);
	}

	/**
	 * Index page.
	 */
	@Action(alias = ALIAS_INDEX_NAME)
	@Transaction
	public void view() {
		log.debug("index view");

		// prepare question date
		now = new JDateTime();
		questionDate = date != null ? DateUtil.toJDateTime(date.intValue()) : now;
		if (questionDate.isAfterDate(now)) {
			questionDate = now;			// don't peek in the future
		}

		// prepare question
		question = questionService.findQuestionForDate(questionDate);
		if (questionDate.equalsDate(now)) {
			currentQuestion = question;		// question is never null since there will be at least one active question
		} else {
			currentQuestion = questionService.findQuestionForDate(now);	// for simplification, we expect there is at least one active question in db
		}
		if (question == null) {
			questionDate = now;
			question = currentQuestion;
		}
		//noinspection ConstantConditions
		isActiveQuestion = question.equals(currentQuestion);

		// prepare answers
		answerService.loadAnswers(question);
		statsService.calcAnswerDistribution(question);

		// prepare the next question (from now! and not the questionDate)
		nextQuestion = questionService.findNextQuestion(question);
		if (nextQuestion != null) {
			nextQuestionDate = DateUtil.toJDateTime(nextQuestion.getDate());
			daysToNextQuestion = nextQuestionDate.daysBetween(now);
		}

		// prepare previous question
		prevQuestion = questionService.findPreviousQuestion(question);

		// prepare random question
		randomQuestion = questionService.findRandomPastQuestion(now);

		// detect favorites and registered only
		if (userSession != null) {
			saved = favoritesService.isFavorite(question, userSession.getUser());
		}
		if (question.isRegisteredOnly() && userSession == null) {
			// reject if the question is only for registered users and no one is signed in
			registeredOnlyWarn = true;
		}
		if (!isActiveQuestion && userSession == null) {
			// reject if user is not signed in and it is not an active question.
			registeredOnlyWarn = true;
		}

		// find userVote and userAnswer
		if (userSession != null) {
			// signed user does not need cookies
			userAnswer = answerService.findUserAnswerForQuestion(userSession.getUser(), question);
		} else {
			// unsigned user reads cookies
			if (cookie == null) {
				return;
			}
			// process cookies
			userVoteId = cookieWorker.processAppCookiesForUserVote(question, cookie);
			if (userVoteId != null) {
				userAnswer = answerService.findAnswerByVoteId(userVoteId);
			}
		}
	}

	/**
	 * Saves current question as favorite.
	 * Ajax method.
	 */
	@PostAction
	@ReadWriteTransaction
	public String save() {
		if (log.isDebugEnabled()) {
			log.debug("save " + question.getId() + ": " + saved);
		}

		User user = userSession.getUser();
		if (user == null) {
			return RAW + "undefined";
		}

		boolean result = false;
		boolean isFavorite = favoritesService.isFavorite(question, user);
		if (isFavorite == true) {
			favoritesService.removeFavorite(question, user);
		} else {
			favoritesService.storeFavorite(question, user);
			result = true;
		}
		return RAW + result;
	}

}