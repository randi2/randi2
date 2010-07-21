package de.randi2.utility;

import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;

import javax.sql.DataSource;

import liquibase.FileSystemFileOpener;
import liquibase.Liquibase;

import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.ext.hsqldb.HsqldbDataTypeFactory;
import org.dbunit.operation.DatabaseOperation;
import org.springframework.beans.factory.annotation.Autowired;

public class InitializeDatabaseUtil {

	@Autowired
	private DataSource dataSource;
	
	public void setUpDatabaseFull() throws Exception {
		try {
			setUpDatabase(new FlatXmlDataSet(new File(
			"src/test/resources/dbunit/testdata.xml")));
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	public void setUpDatabaseEmpty() throws Exception {
		setUpDatabase(new FlatXmlDataSet(new File(
				"src/test/resources/dbunit/emptytestdata.xml")));
	}
	
	public void setUpDatabaseUserAndTrialSites() throws Exception {
		setUpDatabase(new FlatXmlDataSet(new File(
				"src/test/resources/dbunit/userAndTrialSitesTestdata.xml")));
	}
	
	private void setUpDatabase(IDataSet dataSet) throws Exception{
		// initialize your database connection here
		Connection jdbcConnection = dataSource.getConnection();

		Liquibase liquibase = new Liquibase(
				"src/main/resources/META-INF/database/database_changelog.xml",
				new FileSystemFileOpener(), jdbcConnection);
		liquibase.update("init");

		ResultSet resultSet = jdbcConnection
				.createStatement()
				.executeQuery(
						"SELECT * FROM DATABASECHANGELOG WHERE ID='init_ForeignKeyConstraint';");

		if (resultSet.next()) {
			Liquibase liquibase2 = new Liquibase(
					"src/test/resources/liquibase/removeConstraints.xml",
					new FileSystemFileOpener(), jdbcConnection);
			liquibase2.update("init");
			jdbcConnection
			.createStatement()
			.executeUpdate(
					"DELETE FROM DATABASECHANGELOG WHERE ID='init_ForeignKeyConstraint' OR ID='remove_constraints_1';");
		}
		IDatabaseConnection connection = new DatabaseConnection(jdbcConnection);

		// FIXME remove it with dbunit version 2.3
		if (liquibase.getDatabase().getDatabaseProductName().equals(
				"HSQL Database Engine")) {
			DatabaseConfig config = connection.getConfig();
			config.setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY,
					new HsqldbDataTypeFactory());
		}

		try {
//			DatabaseOperation.DELETE_ALL.execute(connection, dataSet);
			DatabaseOperation.CLEAN_INSERT.execute(connection, dataSet);
			jdbcConnection.commit();

			liquibase = new Liquibase(
					"src/main/resources/META-INF/database/database_changelog.xml",
					new FileSystemFileOpener(), jdbcConnection);
			liquibase.update("init2");

		} finally {
			connection.close();
		}
	}
}
