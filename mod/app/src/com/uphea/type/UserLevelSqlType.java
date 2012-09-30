package com.uphea.type;

import com.uphea.domain.UserLevel;
import jodd.db.type.SqlType;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Sql type for {@link UserLevel}. Converts to and form user level enumeration
 * and database value.
 */
public class UserLevelSqlType extends SqlType<UserLevel> {

	@Override
	public void set(PreparedStatement st, int index, UserLevel value, int dbSqlType) throws SQLException {
		st.setInt(index, value.getValue());
	}

	@Override
	public UserLevel get(ResultSet rs, int index, int dbSqlType) throws SQLException {
		return UserLevel.valueOf(rs.getInt(index));
	}
}
