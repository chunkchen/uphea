package com.uphea.action;

import com.uphea.domain.User;
import com.uphea.domain.UserUid;
import com.uphea.interceptor.CaptchaInterceptor;
import com.uphea.service.EmailBuilder;
import com.uphea.service.UserService;
import com.uphea.service.UserUidService;
import jodd.joy.madvoc.action.AppAction;
import jodd.joy.madvoc.meta.PostAction;
import jodd.jtx.meta.Transaction;
import jodd.madvoc.interceptor.DefaultWebAppInterceptors;
import jodd.madvoc.meta.Action;
import jodd.madvoc.meta.In;
import jodd.madvoc.meta.InterceptedBy;
import jodd.madvoc.meta.MadvocAction;
import jodd.petite.meta.PetiteInject;
import nl.captcha.Captcha;

/**
 * Action for handling lost passwords. It is intercepted by default interceptor stack
 * and with {@link CaptchaInterceptor}. This is an example of using <code>DefaultWebAppInterceptors</code>
 * stack that replaces whatever are default interceptors.
 */
@MadvocAction
@InterceptedBy({DefaultWebAppInterceptors.class, CaptchaInterceptor.class})
public class LostPasswordAction extends AppAction {

	@PetiteInject
	UserService userService;

	@PetiteInject
	UserUidService userUidService;

	@PetiteInject
	EmailBuilder emailBuilder;

	@Action
	public void view() {
	}

	@Action
	public void ok() {
	}

	Captcha captcha;

	@In
	String email;

	@In
	String captchaAnswer;

	@PostAction
	@Transaction
	public String execute() {
		if ((captcha != null) && captcha.isCorrect(captchaAnswer)) {
			User user = userService.findUserByEmail(email);

			if (user != null) {
				UserUid uid = userUidService.createUidForUser(user);
				emailBuilder.createLostPasswordMessage(user, uid);
			}

			return REDIRECT + alias(this, "ok");
		}
		return REDIRECT + BACK;
	}
}
