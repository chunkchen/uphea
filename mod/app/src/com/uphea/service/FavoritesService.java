package com.uphea.service;

import com.uphea.domain.Favorites;
import com.uphea.domain.Question;
import com.uphea.domain.User;
import jodd.db.oom.DbOomQuery;
import jodd.joy.db.AppDao;
import jodd.petite.meta.PetiteBean;
import jodd.petite.meta.PetiteInject;

import java.util.List;

import static jodd.db.oom.DbOomQuery.query;
import static jodd.db.oom.sqlgen.DbSqlBuilder.sql;

/**
 */
@PetiteBean
public class FavoritesService {

	@PetiteInject
	AppDao appDao;


	/**
	 * Finds user favorites or <code>null</code> if user has not marked question as favorite.
	 */
	protected Favorites findUserFavorite(Question question, User user) {
		DbOomQuery dbOom = query(sql("select $C{f.*} from $T{Favorites f} where $f.questionId = :questionId and $f.userId = :userId"));
		dbOom.setLong(1, question.getId());
		dbOom.setLong(2, user.getId());
		return (Favorites) dbOom.findAndClose();
	}

	/**
	 * Returns <code>true</code> if a question is favorite for a user.
	 */
	public boolean isFavorite(Question question, User user) {
		return findUserFavorite(question, user) != null;
	}

	/**
	 * Stores favorite.
	 */
	public Favorites storeFavorite(Question question, User user) {
		Favorites favorites = new Favorites(question.getId(), user.getId());
		return appDao.store(favorites);
	}

	/**
	 * Deletes favorite.
	 */
	public void removeFavorite(Question question, User user) {
		Favorites favorites = findUserFavorite(question, user);
		if (favorites == null) {
			return;
		}
		appDao.deleteById(favorites);
	}

	/**
	 * Find favorite question for user.
	 */
	public List<Question> findUserFavorites(User user){
		DbOomQuery dbOom = query(sql("select $C{q.*} from $T{Question q} join $T{Favorites f} on $q.id = $f.questionId where $f.userId = :userId order by $q.date desc"));
		dbOom.setInteger("userId", user.getId());
		return dbOom.listAndClose(Question.class);
	}

}
