package com.uphea.domain;

/**
 * User levels enumeration. It holds 'magic-numbers' used in database.
 * Since this is a custom type that should be used for database mapping,
 * Jodd needs to know about it. Therefore, there is
 * {@link com.uphea.type.UserLevelSqlType sql type} and
 * {@link com.uphea.type.UserLevelTypeConverter type converter}.
 */
public enum UserLevel {
	
	USER(1),
	ADMIN(100);

	final int value;

	UserLevel(int value) {
		this.value = value;
	}

	/**
	 * Returns int value for user level.
	 */
	public int getValue() {
		return value;
	}

	/**
	 * Returns correct user level from int value.
	 */
	public static UserLevel valueOf(int value) {
		switch (value) {
			case 1:		return USER;
			case 100:	return ADMIN;
		}
		throw new IllegalArgumentException("Invalid user level value: '" + value + "'.");
	}
	
}
