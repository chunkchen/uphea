package com.uphea;

import com.uphea.action.IndexAction;
import jodd.madvoc.component.MadvocConfig;
import jodd.servlet.CsrfShield;
import jodd.upload.impl.AdaptiveFileUploadFactory;

/**
 * Custom application Madvoc configuration. This is also a good place
 * for any other web configuration.
 * <p>
 * Additionally/alternately, 'madvoc.props' is also used for
 * Madvoc configuration.
 */
public class AppMadvocConfig extends MadvocConfig {

	public AppMadvocConfig() {
		fileUploadFactory = new AdaptiveFileUploadFactory();
		setRootPackageOf(IndexAction.class);

		// additional web config
		CsrfShield.setTimeToLive(0);
	}
}
