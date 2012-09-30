package com.uphea.interceptor;


import com.uphea.AppCore;
import com.uphea.domain.User;
import com.uphea.domain.UserSession;
import com.uphea.service.UserAuthManager;
import jodd.joy.auth.AuthenticationInterceptor;

import static jodd.joy.madvoc.action.AppAction.REDIRECT;


/**
 * Application authentication interceptor.
 */
public class AppAuthenticationInterceptor extends AuthenticationInterceptor {

	@Override
	protected Object resultRegistrationSuccess() {
		return REDIRECT + "<registrationSuccess>";
	}

	/**
	 * Try to log user via cookie data.
	 */
	@Override
	protected Object loginViaCookie(String[] cookieData) {
		UserAuthManager userAuthManager = AppCore.ref.getPetite().getBean(UserAuthManager.class);
		Long id;
		try {
			id = Long.valueOf(cookieData[0]);
		} catch (NumberFormatException ignore) {
			return null;
		}
		User user = userAuthManager.findUser(id, cookieData[1]);
		if (user == null) {
			return null;
		}
		userAuthManager.login(user);
		return new UserSession(user);
	}

	/**
	 * Logins user.
	 */
	@Override
	protected Object loginUsernamePassword(String username, String password) {
		UserAuthManager userAuthManager = AppCore.ref.getPetite().getBean(UserAuthManager.class);

		// check username/password
		User user = userAuthManager.findUser(username, password);
		if (user == null) {
			return null;
		}
		// login
		userAuthManager.login(user);
		return new UserSession(user);
	}


	/**
	 * Creates cookie data.
	 */
	@Override
	protected String[] createCookieData(Object sessionObject) {
		UserSession userSession = (UserSession) sessionObject;
		User user = userSession.getUser();
		return new String[] {String.valueOf(user.getId()), user.getHashpw()};
	}

}
