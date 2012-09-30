package com.uphea.action;

import com.uphea.domain.User;
import com.uphea.service.UserService;
import com.uphea.service.UserUidService;
import jodd.joy.jtx.meta.ReadWriteTransaction;
import jodd.joy.madvoc.action.AppAction;
import jodd.joy.madvoc.meta.PostAction;
import jodd.jtx.meta.Transaction;
import jodd.madvoc.meta.Action;
import jodd.madvoc.meta.In;
import jodd.madvoc.meta.InOut;
import jodd.madvoc.meta.MadvocAction;
import jodd.madvoc.meta.Out;
import jodd.petite.meta.PetiteInject;
import jodd.vtor.Violation;
import jodd.vtor.constraint.EqualToDeclaredField;
import jodd.vtor.constraint.Length;
import jodd.vtor.constraint.NotBlank;
import jodd.vtor.constraint.NotNull;

import java.util.List;

/**
 * Recover password actions.
 */
@MadvocAction
public class RecoverAction extends AppAction {

	@PetiteInject
	UserUidService userUidService;

	@PetiteInject
	UserService userService;

	/**
	 * Example of setting different name for injection.
	 */
	@InOut("n")
	String token;

	@Out
	User user;

	/**
	 * Views the reset password page, if token is correct.
	 */
	@Action
	@Transaction
	public String view() {
		Long userId = userUidService.findUserIdByUid(token);
		if (userId != null) {
			user = userService.findUserById(userId);
			return OK;
		}
		return REDIRECT + ALIAS_INDEX;
	}

	@In
	@NotBlank
	@NotNull
	@Length(min = 5, max = 25)
	String newPassword;

	@In
	@NotBlank
	@NotNull
	@Length(min = 5, max = 25)
	@EqualToDeclaredField("newPassword")
	String newPassword2;

	/**
	 * List of validations to show on page.
	 */
	@InOut
	List<Violation> violations;

	/**
	 * Action for changing the password. If validation fails,
	 * we move to view page. We are use moving to send
	 * complex type: list of validations!
	 */
	@PostAction
	@ReadWriteTransaction
	public String changePass() {
		Long userId = userUidService.findUserIdByUid(token);
		if (userId == null) {
			return REDIRECT + ALIAS_INDEX;
		}

		if (validateAction()) {
			user = userService.findUserById(userId);
			// save password
			// send new password
			return OK;
		}
		violations = violations();
		return MOVE + alias(this, "view");
	}
}
