package com.uphea.service;

import com.uphea.domain.EmailMessage;
import jodd.db.oom.DbOomQuery;
import jodd.joy.db.AppDao;
import jodd.petite.meta.PetiteBean;
import jodd.petite.meta.PetiteInject;

import java.util.List;

import static jodd.db.oom.DbOomQuery.query;
import static jodd.db.oom.sqlgen.DbSqlBuilder.sql;

/**
 * Email service
 */
@PetiteBean
public class EmailService {

	@PetiteInject
	AppDao appDao;

	protected int maxRepeatsOnError = 3;
	protected int maxEmailsPerSession = 100;

	/**
	 * Specifies maximum number of emails to be send in one opened email session.
	 */
	public void setMaxEmailsPerSession(int maxEmailsPerSession) {
		this.maxEmailsPerSession = maxEmailsPerSession;
	}

	public int getMaxEmailsPerSession() {
		return maxEmailsPerSession;
	}


	/**
	 * Specifies maximum number of sending repeats when message can not be sent.
	 */
	public void setMaxRepeatsOnError(int maxRepeatsOnError) {
		this.maxRepeatsOnError = maxRepeatsOnError;
	}

	public int getMaxRepeatsOnError() {
		return maxRepeatsOnError;
	}

	// ---------------------------------------------------------------- services

	/**
	 * Stores single email to database,
	 */
	public void storeEmail(EmailMessage emailMessage) {
		appDao.store(emailMessage);
	}

	/**
	 * Returns pending email messages to be sent, ordered from oldest to newest.
	 */
	public List<EmailMessage> findPendingEmails(boolean returnAll) {
		String tsql = "select $C{email.*} from $T{EmailMessage email} where $email.repeatCount < :maxTake order by $email.id";
		if (returnAll == false) {
			tsql += " limit :max";
		}

		DbOomQuery q = query(sql(tsql));

		q.setInteger(1, maxRepeatsOnError);
		if (returnAll == false) {
			q.setInteger(2, maxEmailsPerSession);
		}

		return q.autoClose().list(EmailMessage.class);
	}


	/**
	 * Counts all email messages.
	 */
	public long countEmails() {
		DbOomQuery q = query(sql("select count(1) from $T{EmailMessage email}"));
		return q.autoClose().executeCount();
	}

	/**
	 * Counts pending messages.
	 */
	public long countPendingEmails() {
		DbOomQuery q = query(sql("select count(1) from $T{EmailMessage email} where $email.repeatCount < :maxTake"));
		q.setInteger(1, maxRepeatsOnError);
		return q.autoClose().executeCount();
	}
	/**
	 * Deletes sent message.
	 */
	public void deleteEmail(EmailMessage emailMessage) {
		appDao.deleteById(emailMessage);
	}

	/**
	 * Increments repeat count.
	 */
	public void incrementRepeatCount(EmailMessage emailMessage) {
		DbOomQuery q = query(sql("update $T{EmailMessage email} set $email.repeatCount=:take where $email.id=:id"));
		emailMessage.incrementRepeatCount();
		q.setInteger("take", emailMessage.getRepeatCount());
		q.setLong("id", emailMessage.getId());
		q.autoClose().executeUpdate();
	}

	/**
	 * Updates repeat count to 0 for all message.
	 */
	public void enableAllEmails() {
		DbOomQuery q = query(sql("update $T{EmailMessage email} set $email.repeatCount=0"));
		q.autoClose().executeUpdate();
	}

	/**
	 * Enables single message for sending.
	 */
	public void enableEmail(EmailMessage emailMessage){
		DbOomQuery q = query(sql("update $T{EmailMessage email} set $email.repeatCount=0 where $email.id=:id"));
		q.setLong("id", emailMessage.getId());
		q.autoClose().executeUpdate();
	}

}
