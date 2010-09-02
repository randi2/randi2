package de.randi2.core.integration.modelDatatbase;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.hibernate.StaleObjectStateException;
import org.junit.Before;
import org.junit.Test;
import org.springframework.transaction.annotation.Transactional;

import de.randi2.model.AbstractDomainObject;
import de.randi2.model.Login;
import de.randi2.testUtility.utility.AbstractDomainDatabaseTest;

public class AbstractDomainObjectDatabaseTest extends AbstractDomainDatabaseTest<AbstractDomainObject> {

	private Login domainObject;
	
	public AbstractDomainObjectDatabaseTest() {
		super(AbstractDomainObject.class);
	}
	
	@Before
	public void setUp() {
		super.setUp();
		domainObject = factory.getLogin();
	}
	
	@Test
	@Transactional
	public void testTimestamps() {
		domainObject = factory.getLogin();
		entityManager.persist(domainObject);

		assertNotNull(domainObject.getCreatedAt());
		assertNotNull(domainObject.getUpdatedAt());

		domainObject.setUsername("hello@world.com");
		domainObject = entityManager.merge(domainObject);

		assertTrue(domainObject.getCreatedAt().before(domainObject.getUpdatedAt()) || domainObject.getCreatedAt().equals(domainObject.getUpdatedAt()));
	}

	@Test
	@Transactional
	public void testSave(){
		domainObject = factory.getLogin();
		entityManager.persist(domainObject);
		entityManager.flush();

		assertNotSame(AbstractDomainObject.NOT_YET_SAVED_ID, domainObject.getId());
		assertTrue(domainObject.getId() > 0);

	//	assertEquals(0, domainObject.getVersion());
	}
	

	@Test
	public void testOptimisticLocking() {
		entityManager.persist(domainObject);
		entityManager.flush();
		int version = domainObject.getVersion();
		Login v1 = entityManager.find(Login.class, domainObject.getId());
		entityManager.detach(v1);
		Login v2 = entityManager.find(Login.class, domainObject.getId());
		entityManager.detach(v2);
		
		v1.setPassword("Aenderung$1");
		v1 = entityManager.merge(v1);
		entityManager.flush();
		assertTrue(version < v1.getVersion());
		assertTrue(v2.getVersion() < v1.getVersion());
//		v2.setPassword("Aenderung$2");

		try {
			v2 = entityManager.merge(v2);
			entityManager.flush();
			fail("Should fail because of Version Conflicts");
		} catch (StaleObjectStateException e) {
			entityManager.detach(v2);
		}

		Login v3 = entityManager.find(Login.class, domainObject.getId());
		assertEquals(v1.getPassword(), v3.getPassword());
	}
	
}
