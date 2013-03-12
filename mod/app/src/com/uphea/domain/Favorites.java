package com.uphea.domain;

import jodd.db.oom.meta.DbColumn;
import jodd.db.oom.meta.DbTable;

@DbTable
public class Favorites extends DbEntity {

	@DbColumn
	Long questionId;

	@DbColumn
	Long userId;

	public Favorites() {
	}

	public Favorites(Long questionId, Long userId) {
		this.questionId = questionId;
		this.userId = userId;
	}

	// ---------------------------------------------------------------- set/get

	public Long getQuestionId() {
		return questionId;
	}

	public void setQuestionId(Long questionId) {
		this.questionId = questionId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
}
