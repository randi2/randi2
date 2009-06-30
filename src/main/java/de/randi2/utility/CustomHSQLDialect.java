package de.randi2.utility;

import java.sql.Types;

import org.hibernate.dialect.HSQLDialect;

/**
 * Fix for HSQL native SQL queries. 
 * 
 * @author dschrimpf
 *
 */
public class CustomHSQLDialect extends HSQLDialect {

	 public CustomHSQLDialect()
	    {
	        registerColumnType(Types.BOOLEAN, "boolean");
	        registerHibernateType(Types.BOOLEAN, "boolean");
	    }

}
