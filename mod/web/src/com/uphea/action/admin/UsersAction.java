package com.uphea.action.admin;

import com.uphea.domain.Country;
import com.uphea.domain.User;
import com.uphea.interceptor.AppPreparableInterceptorStack;
import com.uphea.meta.PopupAction;
import com.uphea.service.CountryCache;
import com.uphea.service.UserService;
import jodd.joy.jtx.meta.ReadWriteTransaction;
import jodd.joy.madvoc.action.AppAction;
import jodd.joy.madvoc.meta.JSONAction;
import jodd.joy.madvoc.meta.PostAction;
import jodd.joy.page.PageData;
import jodd.joy.page.PageNav;
import jodd.joy.page.PageRequest;
import jodd.jtx.meta.Transaction;
import jodd.madvoc.interceptor.Preparable;
import jodd.madvoc.meta.Action;
import jodd.madvoc.meta.In;
import jodd.madvoc.meta.InOut;
import jodd.madvoc.meta.InterceptedBy;
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

import java.util.Collection;

@MadvocAction
public class UsersAction extends AppAction implements Preparable {

	private static final Logger log = LoggerFactory.getLogger(UsersAction.class);

	@PetiteInject
	UserService userService;

	@PetiteInject
	CountryCache countryCache;

	@InOut
	@AssertValid
	User user;

	@In
	@NotNull(profiles = "new")
	@NotBlank(profiles = "new")
	@Length(min = 5, max = 25, profiles = "new")
	public String newPassword;

	@In
	@NotNull(profiles = "new")
	@NotBlank(profiles = "new")
	@Length(min = 5, max = 25, profiles = "new")
	@EqualToField(value = "newPassword", profiles = "new")
	public String newPassword2;

	@Out
	Collection<Country> countries;


	@Action
	public void view() {
		page();
	}


	@InOut
	PageRequest pageRequest;
	@Out
	PageData<User> page;
	@Out
	PageNav nav;

	@PostAction
	@Transaction
	public String page() {
		if (pageRequest == null) {
			pageRequest = new PageRequest();
			pageRequest.setSort(1);
		}
		page = userService.page(pageRequest);
		userService.loadCountries(page.getItems());
		nav = new PageNav(page);
		return OK;
	}

	/**
	 * Opens popup editor.
	 */
	@PopupAction
	@Transaction
	public String edit() {
		log.debug("edit user");
		user = userService.findUserById(user);
		countries = countryCache.getAllCountries();
		return OK;
	}

	/**
	 * Saves user.
	 */
	@PostAction
	@InterceptedBy(AppPreparableInterceptorStack.class)
	@ReadWriteTransaction
	public String save() {
		log.debug("save user");
		boolean validate;
		if (user.isPersistent()) {
			validate = validateAction(Vtor.DEFAULT_PROFILE);
		} else {
			validate = validateAction(Vtor.DEFAULT_PROFILE, "new");
		}
		if (!validate) {
			return VTOR_JSON;
		}
		if (user.isPersistent()) {
			userService.storeUser(user);
		} else {
			userService.registerNewUser(user, newPassword);

		}
		return NONE;
	}

	@Override
	@Transaction
	public void prepare() {
		user = userService.findUserById(user);
	}

	@JSONAction
	public String saveValidate() {
		if (user.isPersistent()) {
			validateAction(Vtor.DEFAULT_PROFILE);
		} else {
			validateAction(Vtor.DEFAULT_PROFILE, "new");
		}
		return VTOR_JSON;
	}

	/**
	 * Deletes user.
 	 */
	@PostAction
	@ReadWriteTransaction
	public String delete() {
		log.debug("delete user");
		userService.deleteUser(user.getId());
		return REDIRECT + alias(this, "view");
	}
}