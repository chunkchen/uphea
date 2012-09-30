package com.uphea;

import com.uphea.domain.UserLevel;
import com.uphea.type.UserLevelSqlType;
import com.uphea.type.UserLevelTypeConverter;
import jodd.db.type.SqlTypeManager;
import jodd.joy.core.DefaultAppCore;
import jodd.petite.PetiteContainer;
import jodd.typeconverter.TypeConverterManager;
import jodd.util.SystemUtil;

/**
 * Application core. The central point that starts and stops the application.
 * It also initializes all low-level and core parts: prepares configuration,
 * initializes frameworks, containers, types and so on. It also provides
 * convenient access to application Petite container.
 * <p>
 * Using the core, application can be started and using everywhere!
 * For example, application can be started from the command line,
 * as well as from web application.
 */
public class AppCore extends DefaultAppCore {

	/**
	 * Public static reference to application core.
	 * For special cases of accessing app components
	 * outside the container.
	 */
	public static AppCore ref;

	/**
	 * Default constructor.
	 */
	public AppCore() {
		ref = this;
	}

	protected String upheaDir;

	/**
	 * Initializes system: uphea dir and custom types.
	 */
	@Override
	public void init() {
		// resolves uphea directory
		upheaDir = System.getenv("uphea.dir");

		if (upheaDir == null) {
			upheaDir = System.getProperty("uphea.dir");
		}

		if (upheaDir == null) {
			upheaDir = SystemUtil.getUserDir();
		}
		System.setProperty("uphea.dir", upheaDir);

		System.out.println("uphea.dir: " + upheaDir);

		super.init();
	}

	/**
	 * Logs some important info at the very beginning.
	 */
	@Override
	protected void initLogger() {
		super.initLogger();
		log.info("uphea.dir: " + upheaDir);
	}

	@Override
	protected void ready() {
		super.ready();

		// additional sql types
		SqlTypeManager.register(UserLevel.class, UserLevelSqlType.class);
		TypeConverterManager.register(UserLevel.class, new UserLevelTypeConverter());
	}


	@Override
	protected void startProxetta() {
		super.startProxetta();
		//proxetta.setDebugFolder("d://");
	}

	/**
	 * Creates application petite container.
	 */
	@Override
	protected PetiteContainer createPetiteContainer() {
		return new AppPetiteContainer(proxetta);
	}

	@Override
	protected void startDb() {
		super.startDb();
		//LoggablePreparedStatementFactory.getProxetta().setDebugFolder("d:\\");
	}
}
