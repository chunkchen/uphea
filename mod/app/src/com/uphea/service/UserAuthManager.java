package com.uphea.service;

import com.uphea.domain.User;
import jodd.datetime.JDateTime;
import jodd.joy.crypt.PasswordEncoder;
import jodd.joy.db.AppDao;
import jodd.joy.jtx.meta.ReadWriteTransaction;
import jodd.jtx.meta.Transaction;
import jodd.petite.meta.PetiteBean;
import jodd.petite.meta.PetiteInject;

/**
 * Authentication services.
 * Since they are called from the interceptors, and not only the actions,
 * we have to put TX annotations here.
 */
@PetiteBean
public class UserAuthManager {

	@PetiteInject
	AppDao appDao;

	@PetiteInject
	PasswordEncoder passwordEncoder;

	/**
	 * Checks users email and password.
	 */
	@Transaction
	public User findUser(String email, String rawpwd) {
		User user = appDao.findOneByProperty(User.class, "email", email);
		if (user == null) {
			return null;
		}
		if (passwordEncoder.isPasswordValid(user.getHashpw(), rawpwd) == false) {
			return null;
		}
		return user;
	}

	/**
	 * Finds user.
	 */
	@Transaction
	public User findUser(Long id, String hashpwd) {
		User user = appDao.findOneByProperty(User.class, "id", id);
		if (user == null) {
			return null;
		}
		if (hashpwd == null) {
			return null;
		}
		if (hashpwd.equals(user.getHashpw()) == false) {
			return null;
		}
		return user;
	}

	/**
	 * Logins user.
	 */
	@ReadWriteTransaction
	public void login(User user) {
		appDao.updateProperty(user, "lastLogin", new JDateTime());
	}
}
