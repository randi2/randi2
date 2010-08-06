package de.randi2.core.integration.modelDatatbase;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.hibernate.StaleObjectStateException;
import org.hibernate.classic.Session;
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
		sessionFactory.getCurrentSession().persist(domainObject);

		assertNotNull(domainObject.getCreatedAt());
		assertNotNull(domainObject.getUpdatedAt());

		domainObject.setUsername("hello@world.com");
		sessionFactory.getCurrentSession().update(domainObject);

		assertTrue(domainObject.getCreatedAt().before(domainObject.getUpdatedAt()) || domainObject.getCreatedAt().equals(domainObject.getUpdatedAt()));
	}

	@Test
	@Transactional
	public void testSave(){
		domainObject = factory.getLogin();
		sessionFactory.getCurrentSession().persist(domainObject);
		sessionFactory.getCurrentSession().flush();

		assertNotSame(AbstractDomainObject.NOT_YET_SAVED_ID, domainObject.getId());
		assertTrue(domainObject.getId() > 0);

	//	assertEquals(0, domainObject.getVersion());
	}
	

	@Test
	public void testOptimisticLocking() {
		Session session = sessionFactory.openSession();
		session.save(domainObject);
		session.flush();
		Session session1 = sessionFactory.openSession();
		int version = domainObject.getVersion();
		Login v1 = (Login) session.get(Login.class, domainObject.getId());
		Login v2 = (Login) session1.get(Login.class, domainObject.getId());
		
		v1.setPassword("Aenderung$1");
		session.update(v1);
		session.flush();
		assertTrue(version < v1.getVersion());
		assertTrue(v2.getVersion() < v1.getVersion());
//		v2.setPassword("Aenderung$2");

		try {
			session1.update(v2);
			session1.flush();
			fail("Should fail because of Version Conflicts");
		} catch (StaleObjectStateException e) {
			session1.evict(v2);
		}

		Login v3 = (Login)session1.get(Login.class, domainObject.getId());
		assertEquals(v1.getPassword(), v3.getPassword());
	}
	
}
