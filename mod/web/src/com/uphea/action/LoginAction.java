package com.uphea.action;

import jodd.joy.auth.AuthAction;
import jodd.madvoc.meta.Action;
import jodd.madvoc.meta.InOut;
import jodd.madvoc.meta.MadvocAction;

/**
 * Login action.
 */
@MadvocAction
public class LoginAction extends AuthAction {

	@Action(alias = ALIAS_LOGIN_NAME)
	public void view() {
	}
}
