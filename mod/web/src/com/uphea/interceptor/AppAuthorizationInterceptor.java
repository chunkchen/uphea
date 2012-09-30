package com.uphea.interceptor;

import com.uphea.domain.UserLevel;
import com.uphea.domain.UserSession;
import jodd.joy.auth.AuthorizationInterceptor;
import jodd.joy.madvoc.action.AppAction;
import jodd.madvoc.ActionRequest;

import static jodd.joy.madvoc.action.AppAction.REDIRECT;

/**
 * Application authorization interceptor.
 */
public class AppAuthorizationInterceptor extends AuthorizationInterceptor {

	/**
	 * Performs user authorization when accessing some resource.
	 * This is the most simple scenario: user level indicates type of user
	 * and simply folder names defines access rights.
	 * <p>
	 * Of course, this is just a simple implementation since business requirements
	 * are not complex in this area. Obviously, it is up to developers
	 * to choose how complex authorization they need. One idea is to use
	 * auth annotations on action methods, to use roles and so on.
	 */
	@Override
	protected boolean authorize(ActionRequest request, Object sessionObject) {
		String actionPath = request.getActionPath();
		UserSession userSession = (UserSession) sessionObject;
		int userLevel = userSession == null ? 0 : userSession.getUser().getLevel().getValue();

		if (actionPath.startsWith("/user/")) {
			return userLevel >= UserLevel.USER.getValue();
		}

		if (actionPath.startsWith("/admin/")) {
			return userLevel == UserLevel.ADMIN.getValue();
		}
		return true;
	}

	/**
	 * Since we do not have special access denied page,
	 * we will redirect to index page instead.
	 */
	@Override
	protected Object resultAccessDenied() {
		return REDIRECT + AppAction.ALIAS_INDEX;
	}
}
