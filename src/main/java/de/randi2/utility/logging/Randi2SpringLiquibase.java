package de.randi2.utility.logging;

import liquibase.log.LogFactory;
import liquibase.spring.SpringLiquibase;

import org.slf4j.bridge.SLF4JBridgeHandler;

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
		LogFactory.getLogger().addHandler(new SLF4JBridgeHandler());
		LogFactory.getLogger().setUseParentHandlers(false);
	}
}
