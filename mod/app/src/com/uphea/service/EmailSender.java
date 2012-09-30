package com.uphea.service;

import com.uphea.domain.EmailMessage;
import jodd.mail.Email;
import jodd.mail.MailException;
import jodd.mail.SendMailSession;
import jodd.mail.SmtpServer;
import jodd.petite.meta.PetiteBean;
import jodd.petite.meta.PetiteInject;
import jodd.util.ThreadUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Email sender is a background process that sends queued emails.
 */
@PetiteBean
public class EmailSender implements Runnable {

	private static final Logger log = LoggerFactory.getLogger(EmailSender.class);

	@PetiteInject
	EmailManager emailManager;

	@PetiteInject
	EmailBuilder emailBuilder;

	public void init() {
		log.info("email sender initialized");
		smtpServer = new SmtpServer(host, port, username, password);
		start();
	}

	public void close() {
		log.info("email sender closed");
		shutdown(true);
	}

	// ---------------------------------------------------------------- connection

	protected String host;

	protected int port;

	protected String username;

	protected String password;

	protected SmtpServer smtpServer;

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	// ---------------------------------------------------------------- thread

	protected int threadSleepTime = 60;
	protected int pauseBetweenEmails = 500;

	/**
	 * Specifies thread sleep time in seconds between two mail sessions.
	 */
	public void setThreadSleepTime(int threadSleepTime) {
		this.threadSleepTime = threadSleepTime;
	}

	/**
	 * @see #setThreadSleepTime(int)
	 */
	public int getThreadSleepTime() {
		return threadSleepTime;
	}

	/**
	 * Specifies number of milliseconds between sending of two emails during the batch sending.
	 * Some SMTP servers requires some pause between two sending to protected from spammers.
	 * This pause applies only during batch mail sending, i.e. when email sender is in
	 * SENDING state.
	 */
	public void setPauseBetweenEmails(int pauseBetweenEmails) {
		this.pauseBetweenEmails = pauseBetweenEmails;
	}

	/**
	 * @see #setPauseBetweenEmails(int)
	 */
	public int getPauseBetweenEmails() {
		return pauseBetweenEmails;
	}

	// ---------------------------------------------------------------- thread

	public enum Status {
		IDLE,
		SENDING,
		STOPPED
	}

	protected boolean running;

	protected Status status = Status.IDLE;

	/**
	 * Returns send thread status.
	 */
	public Status getStatus() {
		return status;
	}

	/**
	 * Returns if thread is running or is stopped (or about to be stopped).
	 */
	public boolean isRunning() {
		return running;
	}

	protected Thread senderThread;

	/**
	 * Starts mail sender and creates new thread if not already started.
	 */
	public void start() {
		if (senderThread == null) {
			senderThread = new Thread(this);
			senderThread.setPriority(Thread.NORM_PRIORITY - 1);
			senderThread.start();
			log.info("SenderThread started.");
		} else {
			log.warn("SenderThread already started and is in '" + status.name() + "' status.");
		}
	}

	/**
	 * Shutdowns mailer sender and its thread.
	 * If wait is required, method will block until
	 * thread really stop.
	 */
	public void shutdown(boolean wait) {
		log.info("SenderThread is about to stop.");
		running = false;
		if (wait == true) {
			while (status != Status.STOPPED) {
				ThreadUtil.sleep(100);
			}
		}
		senderThread = null;
	}


	/**
	 * Sender thread periodically checks for new pending messages.
	 */
	@Override
	public void run() {
		running = true;
		while (running) {

			while (status == Status.IDLE) {
				log.debug("sending emails...");

				List<EmailMessage> msgs;
				msgs = emailManager.findPending();
				if (msgs.isEmpty()) {
					break;
				}

				log.info("found {} pending emails", Integer.valueOf(msgs.size()));

				// open
				SendMailSession mailSession = openMailSession();
				if (mailSession == null) {
					status = Status.IDLE;
					break;
				}

				// sending
				status = Status.SENDING;
				int sentCount = 0;
				for (EmailMessage msg: msgs) {
					if (running == false) {
						break;
					}
					if (send(mailSession, msg) == true) {
						emailManager.deleteSent(msg);
						sentCount++;
					} else {
						emailManager.updateFailed(msg);
					}
					if (running == false) {
						break;
					}
					ThreadUtil.sleep(pauseBetweenEmails);
				}

				closeMailSession(mailSession);

				status = Status.IDLE;
				if (sentCount == 0) {
					break;	// no message sent, means that sending channel is broken, so go to big sleep.
				}
			}

			int secondsToSleep = threadSleepTime;
			while (secondsToSleep > 0) {
				if (running == false) {
					break;
				}
				ThreadUtil.sleep(1000L);
				secondsToSleep--;
			}
		}
		status = Status.STOPPED;
	}

	protected Exception lastException;

	/**
	 * Returns last occurred exception.
	 */
	public Exception getLastException() {
		return lastException;
	}

	// ---------------------------------------------------------------- smtp

	/**
	 * Opens email session.
	 */
	protected SendMailSession openMailSession() {
		try {
			SendMailSession sendMailSession = smtpServer.createSession();
			sendMailSession.open();
			lastException = null;
			return sendMailSession;
		} catch (MailException mex) {
			lastException = mex;
			log.error("Unable to open email session", mex);
			return null;
		}
	}

	protected void closeMailSession(SendMailSession sendMailSession) {
		try {
			sendMailSession.close();
			lastException = null;
		} catch (MailException mex) {
			lastException = mex;
			log.error("Closing mail session failed", mex);
		}
	}

	/**
	 * Sends single email in open email session.
	 */
	protected boolean send(SendMailSession mailSession, EmailMessage msg) {
		try {
			log.debug("send email");

			Email email = new Email();
			email.from(msg.getSource()).to(msg.getDestination()).subject(msg.getSubject());

			emailBuilder.applyTemplate(email, msg);

			mailSession.sendMail(email);

			return true;
		} catch (MailException mex) {
			log.error("Sending email failed", mex);
			return false;
		}

	}

}
