package com.uphea.action.user;

import com.uphea.domain.Country;
import com.uphea.domain.Question;
import com.uphea.domain.User;
import com.uphea.domain.UserSession;
import com.uphea.interceptor.AppPreparableInterceptorStack;
import com.uphea.meta.PopupAction;
import com.uphea.service.AnswerService;
import com.uphea.service.CountryCache;
import com.uphea.service.FavoritesService;
import com.uphea.service.QuestionService;
import com.uphea.service.UserService;
import jodd.joy.auth.AuthUtil;
import jodd.joy.jtx.meta.ReadWriteTransaction;
import jodd.joy.madvoc.action.AppAction;
import jodd.joy.madvoc.meta.JSONAction;
import jodd.joy.madvoc.meta.PostAction;
import jodd.jtx.meta.Transaction;
import jodd.madvoc.ScopeType;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.List;

@MadvocAction
public class IndexAction extends AppAction implements Preparable {

	private static final Logger log = LoggerFactory.getLogger(IndexAction.class);

	@PetiteInject
	UserService userService;

	@PetiteInject
	FavoritesService favoritesService;

	@PetiteInject
	QuestionService questionService;

	@PetiteInject
	AnswerService answerService;

	@PetiteInject
	CountryCache countryCache;

	@InOut
	@AssertValid
	User user;

	@In(scope = ScopeType.SESSION, value = AuthUtil.AUTH_SESSION_NAME)
	@Out
	UserSession userSession;

	@Out
	Collection<Country> countries;

	@Out
	List<Question> favorites;

	@Out
	long votesCount;

	@Action
	@Transaction
	public void view() {
		user = userSession.getUser();
		user.setCountry(countryCache.lookupCountry(user.getCountryId()));
		favorites = favoritesService.findUserFavorites(user);
		votesCount = answerService.countUserVotes(user);
	}

	/**
	 * Opens popup editor.
	 */
	@PopupAction
	@Transaction
	public String edit() {
		log.debug("edit user");
		user = userSession.getUser();
		countries = countryCache.getAllCountries();
		return OK;
	}

	/**
	 * Saves user.
	 */
	@PostAction
	@ReadWriteTransaction
	@InterceptedBy(AppPreparableInterceptorStack.class)
	public String save() {
		log.debug("save user");
		if (!validateAction(Vtor.DEFAULT_PROFILE)) {
			return VTOR_JSON;
		}
		user = userService.storeUser(user);
		userSession.setUser(user);
		return NONE;
	}

	@Override
	@Transaction
	public void prepare() {
		user = userService.findUserById(user);
	}

	@JSONAction
	public String saveValidate() {
		validateAction(Vtor.DEFAULT_PROFILE);
		return VTOR_JSON;
	}
}