package de.randi2.core.integration.dao;

import static junit.framework.Assert.fail;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.SessionFactory;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;

import de.randi2.testUtility.utility.InitializeDatabaseUtil;

public abstract class AbstractDaoTest {

	
	@Autowired
	private InitializeDatabaseUtil databaseUtil;
	
	protected EntityManager entityManager;

	@PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
	        this. entityManager = entityManager;
	}
	
	@Before
	public void setUp(){
		try {
			databaseUtil.setUpDatabaseUserAndTrialSites();
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
}
