package com.uphea.db;

import jodd.joy.page.DbPager;
import jodd.joy.page.PageData;
import jodd.joy.page.PageRequest;
import jodd.petite.meta.PetiteBean;

/**
 * App pager.
 */
@PetiteBean
public class AppDbPager {

	protected DbPager dbPager;

	/**
	 * Sets DB pager implementation.
	 */
	public void setDbPager(DbPager dbPager) {
		this.dbPager = dbPager;
	}

	// ---------------------------------------------------------------- delegate

	/**
	 * Delegates to pager implementation.
	 */
	public <T> PageData<T> page(PageRequest pageRequest, String sql, String[] sortColumns, Class... target) {
		return dbPager.page(pageRequest, sql, null, sortColumns, target);
	}

}
