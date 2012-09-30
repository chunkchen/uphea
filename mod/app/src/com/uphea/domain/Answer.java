package com.uphea.domain;

import jodd.db.oom.meta.DbColumn;
import jodd.db.oom.meta.DbTable;
import jodd.joy.db.Entity;

/**
 * Question answer.
 */
@DbTable
public class Answer extends Entity {

	@DbColumn
	Long questionId;

	@DbColumn
	String text;

	/**
	 * Number of votes. Redundant information, but needed for
	 * the sake of performance. It could be a <code>Integer</code>, but
	 * since it has a default value 0, <code>int</code> will work just fine.
	 */
	@DbColumn
	int votes;


	/**
	 * Value of vote percentage * 100.
	 */
	int votesPercent;


	// ---------------------------------------------------------------- rel

	Question question;

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		questionId = question == null ? null : question.getId();
		this.question = question;
	}
	

	// ---------------------------------------------------------------- access

	public Long getQuestionId() {
		return questionId;
	}

	public void setQuestionId(Long questionId) {
		this.questionId = questionId;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public int getVotes() {
		return votes;
	}

	public void setVotes(int votes) {
		this.votes = votes;
	}

	public int getVotesPercent() {
		return votesPercent;
	}

	public void setVotesPercent(int votesPercent) {
		this.votesPercent = votesPercent;
	}

	// ---------------------------------------------------------------- misc

	/**
	 * Increment number of votes.
	 */
	public void incrementVotes() {
		this.votes++;
	}

	/**
	 * Decrement votes count. 
	 */
	public void decrementVotes() {
		this.votes--;
	}
}
