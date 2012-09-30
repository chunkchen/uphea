package com.uphea;

import com.uphea.db.AppDbPager;
import com.uphea.install.DbInstaller;
import com.uphea.service.CountryCache;
import com.uphea.service.EmailSender;
import jodd.joy.core.AppInit;
import jodd.joy.page.db.HsqlDbPager;
import jodd.petite.meta.PetiteBean;
import jodd.petite.meta.PetiteInject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Application initialization for business components.
 * Invoked after the {@link com.uphea.AppCore core} is started and before it is stopped.
 * This bean is inside of app container and (as any other Petite bean) can reach
 * all components that has to be started/initialized on application start.
 */
@PetiteBean("appInit")
public class UpheaInit implements AppInit {

	private static final Logger log = LoggerFactory.getLogger(UpheaInit.class);

	@PetiteInject
	AppDbPager appDbPager;

	@PetiteInject
	CountryCache countryCache;

	@PetiteInject
	EmailSender emailSender;

	@PetiteInject
	AppData appData;

	@PetiteInject
	DbInstaller dbInstaller;

	/**
	 * Initializes app.
	 */
	@Override
	public void init() {
		log.debug("app init");
		log.debug("uphea doc root:" + appData.getDocRoot());

		appDbPager.setDbPager(new HsqlDbPager());

		if (dbInstaller.checkDb() == false) {
			dbInstaller.installDb();
		}

		countryCache.init();
		emailSender.init();
	}

	/**
	 * Closes app.
	 */
	@Override
	public void stop() {
		log.debug("app stop");
		emailSender.close();
	}
}
