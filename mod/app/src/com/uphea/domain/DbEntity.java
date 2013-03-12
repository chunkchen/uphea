package com.uphea.domain;

import jodd.db.oom.meta.DbId;
import jodd.joy.db.Entity;

/**
 * Entity with ID column,
 */
public abstract class DbEntity extends Entity {

	@DbId
	protected Long id;

	@Override
	protected long getEntityId() {
		if (id == null) {
			return 0;
		}
		return id;
	}

	@Override
	protected void setEntityId(long id) {
		if (id == 0) {
			this.id = null;
		} else {
			this.id = Long.valueOf(id);
		}
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}