package com.uphea.domain;

import jodd.db.oom.meta.DbColumn;
import jodd.db.oom.meta.DbTable;
import jodd.joy.db.Entity;

/**
 * Unique user id, used for various email validations.
 */
@DbTable
public class UserUid extends Entity {

	@DbColumn
	protected String uid;

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}
}
