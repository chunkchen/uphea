package com.uphea.jsp;

import com.uphea.domain.Question;
import com.uphea.util.DateUtil;
import jodd.datetime.JDateTime;
import jodd.format.Printf;
import jodd.util.CharUtil;
import jodd.util.StringUtil;

import static jodd.util.StringPool.EMPTY;
import static jodd.util.StringPool.SPACE;

/**
 * Various JSP formatting utilities.
 */
public class Format {

	private static final String[] FROM = new String[] {"<s1>", "</>", "<s2>", "<c1>", "<c2>", "<br>", "<s-1>"};
	private static final String[] TO = new String[] 	{
			"<span class='s1'>",
			"</span>",
			"<span class='s2'>",
			"<span class='c1'>",
			"<span class='c2'>",
			"<br/>",
			"<span class='s-1'>",
	};
	private static final String[] TO_EMPTY = new String[] 	{SPACE, SPACE, SPACE, SPACE, SPACE, SPACE, SPACE};
	private static final String DATE_FORMAT = "DD. MML, YYYY.";
	private static final String IMG = "[img]";

	/**
	 * Converts custom shortcut tags into html.
	 */
	public static String questionToHtml(Question question) {
		String text = textToHtml(question.getText());
		text = StringUtil.replace(text, IMG, "<img src='/img/" + question.getId() + ".jpg' alt='question image'/>");
		return text;
	}

	/**
	 * Converts custom shortcut tags into html.
	 */
	public static String textToHtml(String text) {
		return StringUtil.replace(text, FROM, TO);
	}

	/**
	 * Strips all question tags from the text.
	 */
	public static String textPlain(String text) {
		text = StringUtil.replace(text, FROM, TO_EMPTY);
		text = StringUtil.replace(text, IMG, EMPTY);
		text = trimWhitespaceBlocks(text);
		return text;
	}

	/**
	 * Formats date.
	 */
	public static String fmtDate(JDateTime jd) {
		return jd.toString(DATE_FORMAT);
	}

	public static String fmtTime(JDateTime jd, String pattern) {
		return jd.toString(pattern);
	}

	/**
	 * Formats date.
	 */
	public static String fmtIntDate(int date) {
		return DateUtil.toJDateTime(date).toString(DATE_FORMAT);
	}


	/**
	 * Converts integer representation of fixed float (2 floats digits)
	 * into a string.
	 */
	public static String fixedFloat(int value) {
		int floatPart = value % 100;
		StringBuilder sb = new StringBuilder();
		sb.append(Printf.str("%d", value / 100)).append('.').append(Printf.str("%02d", floatPart));
		return sb.toString();
	}


	public static String trimWhitespaceBlocks(String text) {
		char[] str = text.toCharArray();
		StringBuilder sb = new StringBuilder(text.length());
		boolean wasSpace = false;
		for (char c : str) {
			boolean isWhitespace = CharUtil.isWhitespace(c);
			if (wasSpace) {
				if (isWhitespace) {
					continue;
				}
				wasSpace = false;
			} else {
				if (isWhitespace) {
					wasSpace = true;
				}
			}
			sb.append(c);
		}
		return sb.toString();
	}
}
