package com.uphea.action;

import jodd.madvoc.meta.Action;
import jodd.madvoc.meta.MadvocAction;

/**
 * Error actions.
 */
@MadvocAction
public class ErrorAction {

	/**
	 * Mapped to: /error.404.html
	 */
	@Action("404")
	public void error404() {}

	/**
	 * Mapped to: /error.500.html
	 */
	@Action("500")
	public void error500() {}
	
}