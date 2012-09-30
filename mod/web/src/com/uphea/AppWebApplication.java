package com.uphea;

import jodd.joy.core.DefaultAppCore;
import jodd.joy.core.DefaultWebApplication;

/**
 * Web application. Central point for web layer that starts
 * the {@link AppCore application}. Web application can we
 * started without the container!
 */
public class AppWebApplication extends DefaultWebApplication {

	/**
	 * Creates application core to be used in this web application.
	 */
	@Override
	protected DefaultAppCore createAppCore() {
		return new AppCore();
	}

	/**
	 * Registers application specific Madvoc components.
	 */
	@Override
	protected void registerCustomMadvocComponents() {
		registerComponent(AppMadvocController.class);
		registerComponent(AppMadvocConfig.class);
	}

}