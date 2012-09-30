package com.uphea.service;

import com.uphea.AppData;
import com.uphea.domain.EmailMessage;
import com.uphea.domain.User;
import com.uphea.domain.UserUid;
import jodd.bean.BeanTemplateParser;
import jodd.datetime.JDateTime;
import jodd.io.FileUtil;
import jodd.io.findfile.FindFile;
import jodd.mail.Email;
import jodd.petite.meta.PetiteBean;
import jodd.petite.meta.PetiteInject;
import jodd.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Email builder.
 */
@PetiteBean
public class EmailBuilder {

	public static final int DEFAULT_EMAIL_TEMPLATE = 1;

	@PetiteInject
	EmailService emailService;

	@PetiteInject
	AppData appData;

	private static final Logger log = LoggerFactory.getLogger(EmailBuilder.class);

	private static final String MESSAGE_HTML = "message.html";
	private static final String MESSAGE_SUFFIX = "-message.html";
	private static final String SUBJECT_SUFFIX = "-subject.txt";

	protected String templatePath;
	protected String defaultFromAddress;

	protected BeanTemplateParser beanTemplateParser = new BeanTemplateParser();

	/**
	 * Creates welcome message.
	 */
	public void createWelcomeMessage(User user) {

		EmailMessage emailMessage = new EmailMessage();
		emailMessage.setCreated(new JDateTime());
		emailMessage.setDestination(user.getEmail());
		emailMessage.setSource(defaultFromAddress);
		emailMessage.setTemplate(DEFAULT_EMAIL_TEMPLATE);

		Map<String, Object> ctx = new HashMap<String, Object>(1);
		ctx.put("user", user);

		try {
			loadSubjectAndContent(emailMessage, ctx, "welcome");
		} catch (IOException ioex) {
			log.error("Error creating message", ioex);
		}

		emailService.storeEmail(emailMessage);
	}

	/**
	 * Creates lost password message.
	 */
	public void createLostPasswordMessage(User user, UserUid userUid) {
		EmailMessage emailMessage = new EmailMessage();
		emailMessage.setCreated(new JDateTime());
		emailMessage.setDestination(user.getEmail());
		emailMessage.setSource(defaultFromAddress);
		emailMessage.setTemplate(DEFAULT_EMAIL_TEMPLATE);

		Map<String, Object> ctx = new HashMap<String, Object>(2);
		ctx.put("user", user);
		ctx.put("userUid", userUid);
		ctx.put("app", appData);

		try {
			loadSubjectAndContent(emailMessage, ctx, "lostpwd");
		} catch (IOException ioex) {
			log.error("Error creating message", ioex);
		}

		emailService.storeEmail(emailMessage);
	}

	// ---------------------------------------------------------------- tools

	/**
	 * Loads email content and subject from file.
	 */
	protected void loadSubjectAndContent(EmailMessage emailMessage, Map<String, Object> ctx, String name) throws IOException {

		String template = FileUtil.readString(new File(templatePath, name + MESSAGE_SUFFIX));

		emailMessage.setContent(beanTemplateParser.parse(template, ctx));

		template = FileUtil.readString(new File(templatePath, name + SUBJECT_SUFFIX));

		emailMessage.setSubject(beanTemplateParser.parse(template, ctx));
	}

	// ----------------------------------------------------------------


	/**
	 * Applies the template to form the final message.
	 */
	public void applyTemplate(Email email, EmailMessage emailMessage) {

		FindFile ff = new FindFile();
		ff.setIncludeDirs(false);
		ff.searchPath(templatePath + File.separatorChar + emailMessage.getTemplate());

		File f;
		while ((f = ff.nextFile()) != null) {
			if (f.getName().equals(MESSAGE_HTML)) {
				String template = null;
				try {
					template = FileUtil.readString(f);
				} catch (IOException ioex) {
					log.error("Unable to read email template file", ioex);
				}

				String content = beanTemplateParser.parse(template, emailMessage);
				content = StringUtil.replace(content, "src=\"" + emailMessage.getTemplate() + '/', "src=\"");
				content = StringUtil.replace(content, "src=\"", "src=\"cid:");
				email.addHtml(content);
			} else {
				email.embedFile(f);
			}
		}
	}

}
