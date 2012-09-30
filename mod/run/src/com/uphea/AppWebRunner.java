package com.uphea;

import jodd.joy.WebRunner;

/**
 * Shortcut.
 */
public abstract class AppWebRunner extends WebRunner {

	protected void runWebApp() {
		runWebApp(AppWebApplication.class);
	}

}
