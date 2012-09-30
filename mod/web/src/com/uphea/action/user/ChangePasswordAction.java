package com.uphea.action.user;

import com.uphea.domain.User;
import com.uphea.domain.UserSession;
import com.uphea.exception.UpheaException;
import com.uphea.meta.PopupAction;
import com.uphea.service.UserService;
import jodd.joy.auth.AuthUtil;
import jodd.joy.jtx.meta.ReadWriteTransaction;
import jodd.joy.madvoc.action.AppAction;
import jodd.joy.madvoc.meta.PostAction;
import jodd.jtx.JtxPropagationBehavior;
import jodd.jtx.meta.Transaction;
import jodd.madvoc.ScopeType;
import jodd.madvoc.meta.Action;
import jodd.madvoc.meta.In;
import jodd.madvoc.meta.InOut;
import jodd.madvoc.meta.MadvocAction;
import jodd.petite.meta.PetiteInject;
import jodd.vtor.constraint.EqualToField;
import jodd.vtor.constraint.Length;
import jodd.vtor.constraint.NotBlank;
import jodd.vtor.constraint.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@MadvocAction
public class ChangePasswordAction extends AppAction {

	private static final Logger log = LoggerFactory.getLogger(ChangePasswordAction.class);

	@PetiteInject
	UserService userService;

	@In
	@NotNull
	@NotBlank
	@Length(min = 5, max = 25)
	public String currentPassword;

	@In
	@NotNull
	@NotBlank
	@Length(min = 5, max = 25)
	public String newPassword;

	@In
	@NotNull
	@NotBlank
	@Length(min = 5, max = 25)
	@EqualToField("newPassword")
	public String newPassword2;

	@In(scope = ScopeType.SESSION, value = AuthUtil.AUTH_SESSION_NAME)
	UserSession userSession;

	@InOut
	User user;

	/**
	 * Opens popup editor.
	 */
	@PopupAction
	@Transaction(propagation = JtxPropagationBehavior.PROPAGATION_REQUIRED, readOnly = false, timeout = 100)
	public String edit() {
		log.debug("edit user password");
		user = userSession.getUser();
		if (user == null) {
			return REDIRECT + ALIAS_INDEX;
		}
		return OK;
	}

	/**
	 * Saves user password.
	 */
	@PostAction
	@ReadWriteTransaction
	public String save() {
		log.debug("save user password");
		if (!validateAction()) {
			return VTOR_JSON;
		}
		try {
			userService.changePassword(user, currentPassword, newPassword);
		} catch (UpheaException ignore) {
			addViolation("currentPassword");
			return VTOR_JSON;
		}
		userSession.setUser(user);
		return NONE;
	}

}