package com.uphea.service;

import com.uphea.db.AppDbPager;
import com.uphea.domain.User;
import com.uphea.exception.UpheaException;
import jodd.datetime.JDateTime;
import jodd.joy.crypt.PasswordEncoder;
import jodd.joy.db.AppDao;
import jodd.joy.page.PageData;
import jodd.joy.page.PageRequest;
import jodd.petite.meta.PetiteBean;
import jodd.petite.meta.PetiteInject;

import java.util.List;

/**
 * Users services.
 */
@PetiteBean
public class UserService  {

	@PetiteInject
	AppDao appDao;

	@PetiteInject
	PasswordEncoder passwordEncoder;

	@PetiteInject
	CountryCache countryCache;
	
	@PetiteInject
	AppDbPager appDbPager;


	private static final String[] SORT_FIELDS = new String[] {"$u.email", "$u.lastLogin"};

	/**
	 * Paginates all users.
	 */
	public PageData<User> page(PageRequest pr) {
		String sql = "select $C{u.*} from $T{User u}";

		return appDbPager.page(pr, sql, SORT_FIELDS, User.class);
	}

	/**
	 * Loads countries from cache.
	 */
	public void loadCountries(List<User> items) {
		for (User user : items) {
			user.setCountry(countryCache.lookupCountry(user.getCountryId()));
		}
	}


	/**
	 * Finds user by id.
	 */
	public User findUserById(User user) {
		return appDao.findById(user);
	}

	/**
	 * Finds user by id.
	 */
	public User findUserById(Long userId) {
		return appDao.findById(User.class, userId);
	}

	/**
	 * Finds user by email.
	 */
	public User findUserByEmail(String email) {
		return appDao.findOneByProperty(User.class, "email", email.trim().toLowerCase());
	}

	/**
	 * Stores user and password.
	 */
	public User registerNewUser(User user, String password) {
		user.setHashpw(passwordEncoder.encodePassword(password));
		JDateTime now = new JDateTime();
		user.setSince(now);
		user.setLastLogin(now);
		return appDao.store(user);
	}

	/**
	 * Updates user.
	 */
	public User storeUser(User user) {
		return appDao.store(user);
	}

	/**
	 * Changes users password.
	 */
	public User changePassword(User user, String newPassword) {
		user = findUserById(user);		
		user.setHashpw(passwordEncoder.encodePassword(newPassword));
		return appDao.store(user);
	}

	/**
	 * Changes users password.
	 */
	public User changePassword(User user, String currentPassword, String newPassword) {
		user = findUserById(user);
		if (!passwordEncoder.isPasswordValid(user.getHashpw(), currentPassword)) {
			throw new UpheaException();
		}
		user.setHashpw(passwordEncoder.encodePassword(newPassword));
		return appDao.store(user);
	}

	/**
	 * Deletes user.
	 */
	public void deleteUser(Long userId) {
		appDao.deleteById(User.class, userId);
	}

}