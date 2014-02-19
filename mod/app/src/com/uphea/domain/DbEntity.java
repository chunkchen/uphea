package com.uphea.domain;

import jodd.db.oom.meta.DbId;
import jodd.joy.db.Entity;

/**
 * Entity with ID column,
 */
public abstract class DbEntity {

	@DbId
	protected Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public boolean isPersistent() {
		if (id == null) {
			return false;
		}
		if (id.longValue() == 0) {
			return false;
		}
		return true;
	}
}