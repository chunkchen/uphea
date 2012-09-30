package com.uphea.domain;

import jodd.datetime.JDateTime;
import jodd.db.oom.meta.DbColumn;
import jodd.db.oom.meta.DbTable;
import jodd.joy.db.Entity;

/**
 * Email message.
 */
@DbTable
public class EmailMessage extends Entity {

	@DbColumn
	int template;

	@DbColumn
	String source;

	@DbColumn
	String destination;

	@DbColumn
	String subject;

	@DbColumn
	String content;

	@DbColumn
	int repeatCount;

	@DbColumn
	JDateTime created;

	// ---------------------------------------------------------------- accessors

	public int getTemplate() {
		return template;
	}

	public void setTemplate(int template) {
		this.template = template;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getRepeatCount() {
		return repeatCount;
	}

	public void setRepeatCount(int repeatCount) {
		this.repeatCount = repeatCount;
	}

	public JDateTime getCreated() {
		return created;
	}

	public void setCreated(JDateTime created) {
		this.created = created;
	}

	// ---------------------------------------------------------------- misc

	public void incrementRepeatCount() {
		repeatCount++;
	}
}
