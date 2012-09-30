package com.uphea.action.json;

import com.uphea.domain.Answer;
import com.uphea.domain.Question;
import com.uphea.jsp.Format;
import com.uphea.service.AnswerService;
import com.uphea.service.QuestionService;
import jodd.datetime.JDateTime;
import jodd.jtx.meta.Transaction;
import jodd.madvoc.meta.Action;
import jodd.madvoc.meta.MadvocAction;
import jodd.petite.meta.PetiteInject;

import java.util.List;

/**
 * Returns current question and answers as JSON formatted string.
 * We are not using external library for the sake of simplicity.
 */
@MadvocAction
public class QuestionAction {

	@PetiteInject
	QuestionService questionService;

	@PetiteInject
	AnswerService answerService;

	/**
	 * JSON action, with no extension.
	 */
	@Action(extension = Action.NONE)
	@Transaction
	public String view() {
		Question question = questionService.findQuestionForDate(new JDateTime());
		answerService.loadAnswers(question);

		StringBuilder json = new StringBuilder();
		json.append('{');
		json.append("\"question\": \"").append(Format.textPlain(question.getText())).append("\",");
		json.append("\"answers\": [");
		List<Answer> answers = question.getAnswers();
		for (int i = 0, answersSize = answers.size(); i < answersSize; i++) {
			if (i != 0) {
				json.append(',');
			}
			Answer answer = answers.get(i);
			json.append('"');
			json.append(answer.getText());
			json.append('"');
		}
		json.append(']');
		json.append('}');

		return "text:" + json;
	}
}
