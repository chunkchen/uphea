package com.uphea.service;

import com.uphea.domain.Country;
import jodd.db.oom.sqlgen.DbSqlBuilder;
import jodd.jtx.meta.Transaction;
import jodd.petite.meta.PetiteBean;

import java.util.Collections;
import java.util.List;

import static jodd.db.oom.DbOomQuery.query;
import static jodd.db.oom.sqlgen.DbSqlBuilder.sql;

/**
 * Very simple country cache.
 * <p>
 * Since there is no cache recreation while application is running,
 * there is no need for synchronization either.
 */
@PetiteBean
public class CountryCache {

	protected List<Country> countries;
	protected int totalCountries;

	/**
	 * Cache initialization.
	 */
	@Transaction
	public void init() {
		countries = Collections.unmodifiableList(findAllCountriesSortedByName());
		totalCountries = countries.size();
	}

	/**
	 * Finds all countries.
	 */
	protected List<Country> findAllCountriesSortedByName() {
		DbSqlBuilder dbsql = sql("select $C{c.*} from $T{Country c} order by $c.name");
		return query(dbsql).list(Country.class);
	}

	// ---------------------------------------------------------------- cache

	/**
	 * Lookup country by the country id.
	 */
	public Country lookupCountry(Long countryId) {
		if (countryId == null) {
			return null;
		}
		// forcing iterating loop over java5
		for (int i = 0; i < totalCountries; i++) {
			Country country = countries.get(i);
			if (country.getId().equals(countryId)) {
				return country;
			}
		}
		return null;
	}

	/**
	 * Returns list of all countries.
	 */
	public List<Country> getAllCountries() {
		return countries;
	}
}