package com.uphea.action.admin;

import jodd.madvoc.action.ListMadvocConfig;
import jodd.madvoc.meta.Action;
import jodd.madvoc.meta.MadvocAction;

/**
 * Various Madvoc information.
 */
@MadvocAction
public class MadvocInfoAction extends ListMadvocConfig {

	@Action
	public void view() {
		collectActionConfigs();
		collectActionResults();
		collectActionInterceptors();
	}

}