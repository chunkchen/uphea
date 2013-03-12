package com.uphea.domain;

import jodd.datetime.JDateTime;
import jodd.db.oom.meta.DbColumn;
import jodd.db.oom.meta.DbTable;

/**
 * Single user vote for some answer.
 */
@DbTable
public class Vote extends DbEntity {

	@DbColumn
	Long answerId;

	@DbColumn
	Long userId;

	@DbColumn
	String ipAddress;

	@DbColumn
	JDateTime time;

	// ---------------------------------------------------------------- rel

	Answer answer;

	public Answer getAnswer() {
		return answer;
	}

	public void setAnswer(Answer answer) {
		answerId = answer == null ? null : answer.getId();
		this.answer = answer;
	}

	// ---------------------------------------------------------------- access


	public Long getAnswerId() {
		return answerId;
	}

	public void setAnswerId(Long answerId) {
		this.answerId = answerId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public JDateTime getTime() {
		return time;
	}

	public void setTime(JDateTime time) {
		this.time = time;
	}
}
