package com.uphea.jsp;

import jodd.typeconverter.Convert;

public class Fn {

	public static String test(Object value, String yes, String no) {
		return Convert.toBoolean(value).booleanValue() ? yes : no;
	}
}