package com.uphea.action;

import com.uphea.domain.Country;
import com.uphea.domain.User;
import com.uphea.domain.UserLevel;
import com.uphea.domain.UserSession;
import com.uphea.service.CountryCache;
import com.uphea.service.EmailBuilder;
import com.uphea.service.UserService;
import jodd.joy.auth.AuthUtil;
import jodd.joy.jtx.meta.ReadWriteTransaction;
import jodd.joy.madvoc.action.AppAction;
import jodd.joy.madvoc.meta.JSONAction;
import jodd.joy.madvoc.meta.PostAction;
import jodd.jtx.meta.Transaction;
import jodd.madvoc.ScopeType;
import jodd.madvoc.meta.Action;
import jodd.madvoc.meta.In;
import jodd.madvoc.meta.MadvocAction;
import jodd.madvoc.meta.Out;
import jodd.petite.meta.PetiteInject;
import jodd.vtor.Vtor;
import jodd.vtor.constraint.AssertValid;
import jodd.vtor.constraint.EqualToField;
import jodd.vtor.constraint.Length;
import jodd.vtor.constraint.NotBlank;
import jodd.vtor.constraint.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * User registration action.
 */
@MadvocAction
public class RegistrationAction extends AppAction {

	private static final Logger log = LoggerFactory.getLogger(RegistrationAction.class);

	@PetiteInject
	UserService userService;

	@PetiteInject
	EmailBuilder emailBuilder;

	@PetiteInject
	CountryCache countryCache;

	@In
	@AssertValid(profiles = "*")
	User user;

	/**
	 * Current user session if available.
	 */
	@In(scope = ScopeType.SESSION, value = AuthUtil.AUTH_SESSION_NAME)
	UserSession userSession;

	/**
	 * New user session for registered user.
	 */
	@Out(AuthUtil.AUTH_SESSION_NAME)
	UserSession newUserSession;

	@Out
	List<Country> countries;

	@In
	@NotNull @NotBlank @Length(min = 5, max = 25)
	public String newPassword;

	@In
	@NotNull @NotBlank @Length(min = 5, max = 25) @EqualToField(value = "newPassword")
	public String newPassword2;

	@Action
	@Transaction
	public String view() {
		log.debug("registration page");
		if (userSession != null) {
			log.debug("Can't register while logged in");
			return REDIRECT + ALIAS_INDEX;
		}
		countries = countryCache.getAllCountries();
		return null;
	}

	/**
	 * Saves user.
	 */
	@PostAction
	@ReadWriteTransaction
	public String save() {
		log.debug("save registration");
		if (userSession != null) {
			log.debug("Can't register while logged in");
			return REDIRECT + ALIAS_INDEX;
		}

		// detect if user is new
		boolean valid = validateAction(Vtor.DEFAULT_PROFILE);
		if (user.getEmail() != null && (userService.findUserByEmail(user.getEmail()) != null)) {
			addViolation("user.email");
		}
		if (!valid) {
			return VTOR_JSON;
		}

		// set country
		Country userCountry = countryCache.lookupCountry(user.getCountryId());
		user.setCountry(userCountry);

		// set level for user
		user.setLevel(UserLevel.USER);
		user = userService.registerNewUser(user, newPassword);

		// send email
		emailBuilder.createWelcomeMessage(user);

		newUserSession = new UserSession(user);
		return CHAIN + "/j_register";
	}

	@Action(alias = "registrationSuccess")
	public void success() {
	}

	/**
	 * Validates user before saves and during live validation.
	 */
	@JSONAction
	@Transaction
	public String saveValidate() {
		boolean valid = validateAction(Vtor.DEFAULT_PROFILE);
		if (user.getEmail() != null && (userService.findUserByEmail(user.getEmail()) != null)) {
			addViolation("user.email");
		}
		return VTOR_JSON;
	}

}