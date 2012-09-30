package com.uphea.action.admin;

import com.uphea.service.EmailSender;
import com.uphea.service.EmailService;
import jodd.jtx.meta.Transaction;
import jodd.madvoc.meta.Action;
import jodd.madvoc.meta.MadvocAction;
import jodd.madvoc.meta.Out;
import jodd.petite.meta.PetiteInject;

@MadvocAction
public class EmailStatusAction {

	@PetiteInject
	EmailService emailService;
	@PetiteInject
	EmailSender emailSender;

	@Out
	long pendingEmailsCount;

	@Out
	long allEmailsCount;

	@Out
	Exception mailException;

	@Out
	String status;


	@Action
	@Transaction
	public void view() {
		pendingEmailsCount = emailService.countPendingEmails();
		allEmailsCount = emailService.countEmails();
		mailException = emailSender.getLastException();
		status = emailSender.getStatus().toString();
	}
}
