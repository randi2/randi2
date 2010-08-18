package de.randi2.utility.logging;

import liquibase.integration.spring.SpringLiquibase;
import liquibase.logging.LogFactory;
import liquibase.logging.LogLevel;

/**
 * 
 * Own SpringLiquibase class to switch off the own logging of liquibase and added it to log4j.
 * 
 * @author dschrimpf
 *
 */
public class Randi2SpringLiquibase extends SpringLiquibase {

	
	public Randi2SpringLiquibase() {
		super();
		LogFactory.getLogger().setLogLevel(LogLevel.WARNING);
//		LogFactory.getLogger().addHandler(new SLF4JBridgeHandler());
//		LogFactory.getLogger().setUseParentHandlers(false);
	}
}
