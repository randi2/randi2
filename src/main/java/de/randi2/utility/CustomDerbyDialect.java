package de.randi2.utility;

import java.sql.Types;
import org.hibernate.dialect.DerbyDialect;

/**
 * Fix for known CLOB>255 bug in hibernates org.hibernate.dialect.DerbyDialect
 *
 * @author haraldaamot
 *
 */

public class CustomDerbyDialect extends DerbyDialect {

	public CustomDerbyDialect() {
		super();
		registerColumnType(Types.CLOB, "clob");
		registerHibernateType(Types.CLOB, "clob");
	}

}
