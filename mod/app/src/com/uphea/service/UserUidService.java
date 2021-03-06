package com.uphea.service;

import com.uphea.domain.User;
import com.uphea.domain.UserUid;
import jodd.joy.db.AppDao;
import jodd.petite.meta.PetiteBean;
import jodd.petite.meta.PetiteInject;
import jodd.util.RandomStringUtil;

@PetiteBean
public class UserUidService {

	@PetiteInject
	AppDao appDao;

	/**
	 * Creates unique identificator for given user.
	 * If identification exist it will be replaced.
	 */
	public UserUid createUidForUser(User user) {

		Long userId = user.getId();

		UserUid uid = appDao.findById(UserUid.class, userId);
		if (uid != null) {
			uid.setUid(generateNewUid());
			appDao.updateProperty(uid, "uid", uid.getUid());
		} else {
			uid = new UserUid();
			uid.setId(userId);
			uid.setUid(generateNewUid());
			// ID is not autogenerated, therefore it is created manually.
			// Hence, we are calling save(), instead store().
			appDao.save(uid);
		}
		return uid;
	}

	/**
	 * Creates new uid.
	 */
	protected String generateNewUid() {
		return RandomStringUtil.randomAlphaNumeric(10);
	}

	/**
	 * Finds user id by uid.
	 */
	public Long findUserIdByUid(String uidValue) {
		if (uidValue == null) {
			return null;
		}
		UserUid uid = appDao.findOneByProperty(UserUid.class, "uid", uidValue);
		if (uid == null) {
			return null;
		}
		return uid.getId();
	}
}
