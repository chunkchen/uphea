package com.uphea.domain;

import jodd.db.oom.meta.DbColumn;
import jodd.db.oom.meta.DbTable;

import java.util.ArrayList;
import java.util.List;

/**
 * Question entity. Besides simple mappings, question has related
 * {@link Country country) and list of {@link Answer answers}.
 */
@DbTable
public class Question extends DbEntity {

	@DbColumn
	Long countryId;

	@DbColumn
	int date;

	@DbColumn
	String text;

	@DbColumn
	boolean registeredOnly;

	/**
	 * Total number of votes for this question.
	 */
	int totalVotes;

	// ---------------------------------------------------------------- rel

	Country country;

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		countryId = country == null ? null : country.getId();
		this.country = country;
	}


	List<Answer> answers = new ArrayList<Answer>();

	public List<Answer> getAnswers() {
		return answers;
	}

	public void setAnswers(List<Answer> answers) {
		this.answers = answers;
		for (Answer answer : answers) {
			answer.setQuestion(this);
		}
	}

	public void addAnswer(Answer answer) {
		answers.add(answer);
		answer.setQuestion(this);
	}

	public Answer lookupAnswerById(Long id) {
		for (Answer answer : answers) {
			if (answer.getId().equals(id)) {
				return answer;
			}
		}
		return null;
	}



	// ---------------------------------------------------------------- access


	public Long getCountryId() {
		return countryId;
	}

	public void setCountryId(Long countryId) {
		this.countryId = countryId;
	}

	public int getDate() {
		return date;
	}

	public void setDate(int date) {
		this.date = date;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public boolean isRegisteredOnly() {
		return registeredOnly;
	}

	public void setRegisteredOnly(boolean registeredOnly) {
		this.registeredOnly = registeredOnly;
	}

	public int getTotalVotes() {
		return totalVotes;
	}

	public void setTotalVotes(int totalVotes) {
		this.totalVotes = totalVotes;
	}
}
