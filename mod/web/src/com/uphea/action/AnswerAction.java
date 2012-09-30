package com.uphea.action;

import com.uphea.domain.Answer;
import com.uphea.domain.Question;
import com.uphea.domain.UserSession;
import com.uphea.domain.Vote;
import com.uphea.service.AnswerService;
import com.uphea.service.QuestionService;
import com.uphea.service.StatsService;
import com.uphea.worker.CookieWorker;
import jodd.joy.jtx.meta.ReadWriteTransaction;
import jodd.joy.madvoc.action.AppAction;
import jodd.joy.madvoc.meta.PostAction;
import jodd.madvoc.ScopeType;
import jodd.madvoc.meta.In;
import jodd.madvoc.meta.MadvocAction;
import jodd.madvoc.meta.Out;
import jodd.petite.meta.PetiteInject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.Cookie;

import static jodd.joy.auth.AuthUtil.AUTH_SESSION_NAME;

/**
 * Answer action handles answers. 
 */
@MadvocAction
public class AnswerAction extends AppAction {

	private static final Logger log = LoggerFactory.getLogger(AnswerAction.class);

	@PetiteInject
	QuestionService questionService;

	@PetiteInject
	AnswerService answerService;

	@PetiteInject
	CookieWorker cookieWorker;

	@PetiteInject
	StatsService statsService;

	@In
	Answer answer;

	@In
	Vote userVote;

	@Out
	Long userVoteId;

	@In(scope = ScopeType.SESSION, value = AUTH_SESSION_NAME)
	UserSession userSession;

	@In(scope = ScopeType.SERVLET)
	boolean csrfTokenValid;

	@In(scope = ScopeType.SERVLET)
	String requestRemoteAddr;

	@Out(scope = ScopeType.SERVLET)
	Cookie cookie;

	@Out
	Question question;

	@Out
	Answer userAnswer;


	/**
	 * Vote for some answer.
	 * Main check is done in IndexAction.
	 * Since CSRF is here, we are sure that no request can be fired then ours.
	 */
	@PostAction
	@ReadWriteTransaction
	public String execute() {
		log.debug("vote for: {}", answer);

		if (csrfTokenValid == false) {
			log.warn("csrf token not valid");
			return "csrf";
		}

		// find answers question
		question = questionService.findAnswersQuestion(answer.getId());
		answerService.loadAnswers(question);
		userAnswer = question.lookupAnswerById(answer.getId());

		Vote vote;
		// find user vote if exist
		if (userSession != null) {
			// read vote from db, ignore request parameter
			userVote = answerService.findUserVoteForQuestion(userSession.getUser(), question);
		} else {
			// read votes from param
			if ((userVote != null) && (userVote.getId() != null)) {
				userVote = answerService.findVoteById(userVote.getId());
				if (userVote == null) {
					log.info("user vote can't be found.");
				}
			} else {
				userVote = null;
			}
		}

		// update or save a vote
		if (userVote != null) {
			log.info("update vote {}", userVote.getId());
			Answer currentAnswer = question.lookupAnswerById(userVote.getAnswerId());
			if (currentAnswer.equals(userAnswer)) {
				log.debug("vote equals to previous one.");
				statsService.calcAnswerDistribution(question);
				return OK;
			}
			vote = answerService.changeVoteAnswer(userVote, currentAnswer, userAnswer, requestRemoteAddr);
		} else {
			log.info("add new vote {}");
			vote = answerService.voteForAnswer(userAnswer, requestRemoteAddr, userSession == null ? null : userSession.getUser());
		}
		userVoteId = vote.getId();

		// recalc
		statsService.calcAnswerDistribution(question);

		// add cookie
		cookie = cookieWorker.createVoteCookie(question, vote);

		// return
		return OK;
	}


}
