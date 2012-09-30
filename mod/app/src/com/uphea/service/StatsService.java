package com.uphea.service;

import com.uphea.domain.Answer;
import com.uphea.domain.Question;
import jodd.petite.meta.PetiteBean;

import java.util.List;

/**
 * Various statistics calculators.
 */
@PetiteBean
public class StatsService {

	/**
	 * Calculates answer distribution and sets total votes number
	 * for question.
	 */
	public void calcAnswerDistribution(Question question) {
		int totalVotes = calcAnswerDistribution(question.getAnswers());
		question.setTotalVotes(totalVotes);
	}

	/**
	 * Calculate answer distribution in percentages and return total votes.
	 */
	public int calcAnswerDistribution(List<Answer> answers) {
		int total = 0;
		for (Answer answer : answers) {
			answer.setVotesPercent(0);
			total += answer.getVotes();
		}
		if (total == 0) {
			// no votes yet
			return total;
		}
		int totalVotes = total;
		float t = (float) total;
		total = 0;
		for (Answer answer : answers) {
			float p = (answer.getVotes() * 10000) / t;
			answer.setVotesPercent(Math.round(p));
			total += answer.getVotesPercent();
		}

		// fix to match 100% sum
		int delta = total - 10000;
		for (Answer answerToFix : answers) {
			int percent = answerToFix.getVotesPercent();
			if (percent > 0) {
				if (delta > percent) {
					answerToFix.setVotesPercent(0);
					delta -= percent;
				} else {
					answerToFix.setVotesPercent(percent - delta);
					delta = 0;
				}
			}
			if (delta == 0) {
				break;
			}
		}
		return totalVotes;
	}
}
