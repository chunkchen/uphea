package com.uphea.type;

import com.uphea.domain.UserLevel;
import jodd.typeconverter.TypeConversionException;
import jodd.typeconverter.TypeConverter;
import jodd.util.CharUtil;

/**
 * Type converter for {@link UserLevel} enumeration.
 * Converts from any object into the user level.
 */
public class UserLevelTypeConverter implements TypeConverter<UserLevel> {

	@Override
	public UserLevel convert(Object value) {
		if (value == null) {
			return null;
		}
		if (value instanceof UserLevel) {
			return (UserLevel) value;
		}

		String stringValue = value.toString().trim();

		if ((stringValue.length() > 0) && CharUtil.isDigit(stringValue.charAt(0))) {
			try {
				int intValue = Integer.parseInt(stringValue);
				return UserLevel.valueOf(intValue);
			} catch (Exception ignore) {
			}
		}

		try {
			return UserLevel.valueOf(stringValue);
		} catch (IllegalArgumentException iaex) {
			throw new TypeConversionException(value, iaex);
		}
	}

}
