package com.uphea;

import jodd.petite.meta.PetiteBean;

/**
 * Simple bean that holds global configuration data.
 * Values will be injected from properties files:
 * values of all properties named as: 'appData.XXX' will be
 * injected here (check app-data.properties).
 */
@PetiteBean
public class AppData {

	String docRoot;

	String imgRoot;

	String emailRoot;

	String siteUrl;

	/**
	 * Returns document root.
	 */
	public String getDocRoot() {
		return docRoot;
	}

	/**
	 * Returns image root.
	 */
	public String getImgRoot() {
		return imgRoot;
	}

	/**
	 * Returns email root.
	 */
	public String getEmailRoot() {
		return emailRoot;
	}

	/**
	 * Returns site url.
	 */
	public String getSiteUrl() {
		return siteUrl;
	}
}
