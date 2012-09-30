package com.uphea.action.admin;

import com.uphea.domain.Question;
import com.uphea.service.AnswerService;
import com.uphea.service.QuestionService;
import com.uphea.service.StatsService;
import com.uphea.util.DateUtil;
import jodd.datetime.JDateTime;
import jodd.jtx.meta.Transaction;
import jodd.madvoc.meta.Action;
import jodd.madvoc.meta.In;
import jodd.madvoc.meta.MadvocAction;
import jodd.madvoc.meta.Out;
import jodd.petite.meta.PetiteInject;

/**
 * Previews questions.
 */
@MadvocAction
public class PreviewAction {

	@PetiteInject
	QuestionService questionService;

	@PetiteInject
	AnswerService answerService;

	@PetiteInject
	StatsService statsService;

	@In
	Integer date;

	@Out
	Question question;

	@Out
	Question nextQuestion;

	@Out
	Question prevQuestion;

	@Action
	@Transaction
	public void view() {
		JDateTime questionDate = date != null ? DateUtil.toJDateTime(date.intValue()) : new JDateTime();
		question = questionService.findQuestionForDate(questionDate);
		nextQuestion = questionService.findNextQuestion(question);
		prevQuestion = questionService.findPreviousQuestion(question);
		answerService.loadAnswers(question);
		statsService.calcAnswerDistribution(question);
	}

}
