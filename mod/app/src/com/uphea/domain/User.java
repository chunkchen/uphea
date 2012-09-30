package com.uphea.domain;

import com.uphea.domain.constraint.BirthYear;
import jodd.datetime.JDateTime;
import jodd.db.oom.meta.DbColumn;
import jodd.db.oom.meta.DbTable;
import jodd.joy.db.Entity;
import jodd.joy.vtor.constraint.Email;
import jodd.vtor.constraint.MaxLength;
import jodd.vtor.constraint.NotBlank;
import jodd.vtor.constraint.NotNull;

/**
 * User entity. Since this entity can be edited by users,
 * it contains validation annotations as well.
 */
@DbTable
public class User extends Entity {

	@DbColumn
	protected String hashpw;

	@DbColumn
	protected UserLevel level;

	@DbColumn
	@NotNull @NotBlank @MaxLength(100) @Email
	protected String email;

	@DbColumn	
	@MaxLength(100)
	protected String name;

	@DbColumn
	protected char sex = 'N';

	@DbColumn
	@BirthYear
	protected Integer birthYear;

	@DbColumn
	protected Long countryId;

	@DbColumn
	protected JDateTime lastLogin;

	@DbColumn
	protected JDateTime since;

	/**
	 * todo: currently all users are confirmed. later, email will be used for user confirmation.
	 */
	@DbColumn
	protected boolean confirmed;

	// ---------------------------------------------------------------- rel

	Country country;

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		countryId = country == null ? null : country.getId();
		this.country = country;
	}

	// ---------------------------------------------------------------- accessors

	public String getHashpw() {
		return hashpw;
	}

	public void setHashpw(String hashpw) {
		this.hashpw = hashpw;
	}

	public UserLevel getLevel() {
		return level;
	}

	public void setLevel(UserLevel level) {
		this.level = level;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public JDateTime getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(JDateTime lastLogin) {
		this.lastLogin = lastLogin;
	}

	public JDateTime getSince() {
		return since;
	}

	public void setSince(JDateTime since) {
		this.since = since;
	}

	public char getSex() {
		return sex;
	}

	public void setSex(char sex) {
		this.sex = sex;
	}

	public Integer getBirthYear() {
		return birthYear;
	}

	public void setBirthYear(Integer birthYear) {
		this.birthYear = birthYear;
	}

	public Long getCountryId() {
		return countryId;
	}

	public void setCountryId(Long countryId) {
		this.countryId = countryId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	// ---------------------------------------------------------------- additional

	/**
	 * Returns user name if exist, otherwise his email.
	 */
	public String getScreenName() {
		if (name != null) {
			return name;
		}
		return email;
	}
}