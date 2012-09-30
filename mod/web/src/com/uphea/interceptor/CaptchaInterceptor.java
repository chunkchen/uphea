package com.uphea.interceptor;

import jodd.bean.BeanUtil;
import jodd.madvoc.ActionRequest;
import jodd.madvoc.interceptor.ActionInterceptor;
import nl.captcha.Captcha;

import javax.servlet.http.HttpSession;

/**
 * Interceptor that injects captcha instance from http session if exist.
 */
public class CaptchaInterceptor extends ActionInterceptor {

	private static final String CAPTCHA_FIELD_NAME = "captcha";

	@Override
	public Object intercept(ActionRequest actionRequest) throws Exception {

		HttpSession session = actionRequest.getHttpServletRequest().getSession();
		Captcha captcha = (Captcha) session.getAttribute(Captcha.NAME);

		if (captcha != null) {
			Object action = actionRequest.getAction();
			BeanUtil.setDeclaredPropertySilent(action, CAPTCHA_FIELD_NAME, captcha);
		}

		return actionRequest.invoke();
	}
}
