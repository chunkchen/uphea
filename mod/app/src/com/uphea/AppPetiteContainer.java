package com.uphea;

import jodd.petite.proxetta.ProxettaAwarePetiteContainer;
import jodd.proxetta.impl.ProxyProxetta;

/**
 * Petite container for application. It is based on Proxetta aware Petite container,
 * so app beans can be proxified on bean creation.
 * <p>
 * It is not necessary to create a custom container, but it is a good
 * practice to have it just in case it has to be enhanced. 
 */
public class AppPetiteContainer extends ProxettaAwarePetiteContainer {

	public AppPetiteContainer(ProxyProxetta proxetta) {
		super(proxetta);
	}
}
