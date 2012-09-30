package com.uphea.action.admin;

import com.uphea.domain.User;
import com.uphea.meta.PopupAction;
import com.uphea.service.UserService;
import jodd.joy.jtx.meta.ReadWriteTransaction;
import jodd.joy.madvoc.action.AppAction;
import jodd.joy.madvoc.meta.PostAction;
import jodd.jtx.meta.Transaction;
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
	@NotBlank
	@NotNull
	@Length(min = 5, max = 25)
	public String newPassword;

	@In
	@NotBlank
	@NotNull
	@Length(min = 5, max = 25)
	@EqualToField("newPassword")
	public String newPassword2;

	@InOut
	User user;

	/**
	 * Opens popup editor.
	 */
	@PopupAction
	@Transaction
	public String edit() {
		log.debug("edit user password");
		user = userService.findUserById(user);
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
		userService.changePassword(user, newPassword);
		return NONE;
	}

}