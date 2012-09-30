package com.uphea.service;

import com.uphea.domain.EmailMessage;
import jodd.joy.jtx.meta.ReadWriteTransaction;
import jodd.jtx.meta.Transaction;
import jodd.petite.meta.PetiteBean;
import jodd.petite.meta.PetiteInject;

import java.util.List;

@PetiteBean
public class EmailManager {

	@PetiteInject
	EmailService emailService;

	@Transaction
	public List<EmailMessage> findPending() {
		return emailService.findPendingEmails(false);
	}

	@ReadWriteTransaction
	public void deleteSent(EmailMessage emailMessage) {
		emailService.deleteEmail(emailMessage);
	}

	@ReadWriteTransaction
	public void updateFailed(EmailMessage emailMessage) {
		emailService.incrementRepeatCount(emailMessage);
	}
}
