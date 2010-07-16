package de.randi2.services;

import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

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
import org.hibernate.SessionFactory;
import org.hibernate.context.ManagedSessionContext;
import org.junit.After;
import org.junit.Before;
import org.junit.internal.runners.statements.Fail;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.dao.SystemWideSaltSource;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import de.randi2.model.Login;
import de.randi2.test.utility.DomainObjectFactory;
import de.randi2.utility.security.RolesAndRights;

import static junit.framework.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/META-INF/service-test.xml",
		"/META-INF/subconfig/test.xml" })
public abstract class AbstractServiceTest {

	@Autowired
	protected SessionFactory sessionFactory;
	@Autowired
	protected DomainObjectFactory factory;
	@Autowired
	protected RolesAndRights rolesAndRights;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private SystemWideSaltSource saltSource;
	@Autowired
	private DataSource dataSource;

	protected Login user;

	private void setUpDatabase() throws Exception {
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

		// initialize dataset
		IDataSet dataSet = new FlatXmlDataSet(new File(
				"src/test/resources/dbunit/testdata.xml"));
		try {
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

	@Before
	public void setUp() {
		ManagedSessionContext.bind(sessionFactory.openSession());
		try {
			setUpDatabase();
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@After
	public void afterTest() {
		SecurityContextHolder.getContext().setAuthentication(null);
		ManagedSessionContext.unbind(sessionFactory);
	}

	protected void authenticatAsAdmin() {
		user = findLogin("admin@trialsite1.de");
		AnonymousAuthenticationToken authToken = new AnonymousAuthenticationToken(
				"admin@trialsite1.de", user, new ArrayList<GrantedAuthority>(
						user.getAuthorities()));
		// Perform authentication
		SecurityContextHolder.getContext().setAuthentication(authToken);
		SecurityContextHolder.getContext().getAuthentication()
				.setAuthenticated(true);
	}
	
	protected void authenticatAsPrincipalInvestigator() {
		user = findLogin("p_investigator@trialsite1.de");
		AnonymousAuthenticationToken authToken = new AnonymousAuthenticationToken(
				"admin@trialsite1.de", user, new ArrayList<GrantedAuthority>(
						user.getAuthorities()));
		// Perform authentication
		SecurityContextHolder.getContext().setAuthentication(authToken);
		SecurityContextHolder.getContext().getAuthentication()
				.setAuthenticated(true);
	}
	
	protected void authenticatAsInvestigator() {
		user = findLogin("investigator@trialsite1.de");
		AnonymousAuthenticationToken authToken = new AnonymousAuthenticationToken(
				"admin@trialsite1.de", user, new ArrayList<GrantedAuthority>(
						user.getAuthorities()));
		// Perform authentication
		SecurityContextHolder.getContext().setAuthentication(authToken);
		SecurityContextHolder.getContext().getAuthentication()
				.setAuthenticated(true);
	}

	@SuppressWarnings("unchecked")
	protected Login findLogin(String username) {
		String query = "from de.randi2.model.Login login where "
				+ "login.username =?";

		List<Login> loginList = (List) sessionFactory.getCurrentSession()
				.createQuery(query).setParameter(0, username).list();
		if (loginList.size() == 1)
			return loginList.get(0);
		else
			return null;
	}
}
