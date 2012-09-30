package com.uphea.jsp;

import com.uphea.domain.Question;

/**
 * Some url building functions, usually for url-rewritten paths. 
 */
public class UrlFunctions {

	public static final String URL_QUESTION = "q";

	/**
	 * Renders question url.
	 */
	public static String urlQuestionForDate(Question question) {
		return '/' + URL_QUESTION +'/' + question.getDate();
	}

}
