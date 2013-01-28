package com.uphea.action.admin;

import jodd.bean.BeanUtil;
import jodd.madvoc.action.ListMadvocConfig;
import jodd.madvoc.meta.Action;
import jodd.madvoc.meta.MadvocAction;
import jodd.madvoc.meta.Out;

import java.util.HashMap;
import java.util.Map;

/**
 * Various Madvoc information.
 */
@MadvocAction
public class MadvocInfoAction extends ListMadvocConfig {

	@Out
	Map<String, String> aliases;

	@Action
	public void view() {
		collectActionConfigs();
		collectActionResults();
		collectActionInterceptors();

		// todo move to jodd
		aliases = (Map<String, String>) BeanUtil.getDeclaredProperty(actionsManager, "pathAliases");

		Map<String, String> newMap = new HashMap<String, String>(aliases.size());

		for (Map.Entry<String, String> entry : aliases.entrySet()) {
			newMap.put(entry.getValue(), entry.getKey());
		}

		aliases = newMap;
	}

}