package com.uphea.util;

import jodd.datetime.JDateTime;

/**
 * Time utilities for dates in int-format.
 * Int format is: YYYYMMDD.
 */
public class DateUtil {

	/**
	 * Converts date to int date format.
	 */
	public static int toIntDate(JDateTime jd) {
		return jd.getYear() * 10000 + jd.getMonth() * 100 + jd.getDay();
	}

	/**
	 * Converts date to JDateTime.
	 */
	public static JDateTime toJDateTime(int date) {
		int year = date / 10000;
		date -= year * 10000;
		int month = date / 100;
		date -= month * 100;
		return new JDateTime(year, month, date);
	}

	/**
	 * Returns current date in int format.
	 * @see #toIntDate(jodd.datetime.JDateTime) 
	 */
	public static int currentIntDate() {
		return toIntDate(new JDateTime());
	}

}
