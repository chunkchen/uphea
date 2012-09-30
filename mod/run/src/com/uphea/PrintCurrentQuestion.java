package com.uphea;

import com.uphea.domain.Question;
import com.uphea.service.QuestionService;
import jodd.datetime.JDateTime;

/**
 * Prints current question.
 * An example how to use services.
 */
public class PrintCurrentQuestion {

	public static void main(String[] args) {
		new AppWebRunner() {
			@Override
			public void run() {
				QuestionService qs = petite.getBean(QuestionService.class);
				Question q = qs.findQuestionForDate(new JDateTime());
				System.out.println(q.getText());
			}
		}.runWebApp();
	}

}
