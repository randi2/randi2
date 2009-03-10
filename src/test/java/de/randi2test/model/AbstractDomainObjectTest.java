package de.randi2test.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.orm.hibernate3.HibernateOptimisticLockingFailureException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import de.randi2.model.AbstractDomainObject;
import de.randi2.model.Login;
import de.randi2test.utility.AbstractDomainTest;
import java.util.GregorianCalendar;
import org.junit.Ignore;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/META-INF/spring-test.xml"})
public class AbstractDomainObjectTest extends AbstractDomainTest<AbstractDomainObject> {

	private Login domainObject;

	public AbstractDomainObjectTest() {
		super(AbstractDomainObject.class);
	}

	@Before
	public void setUp() {
		domainObject = factory.getLogin();
	}

	@Test
	public void testCreate() {
		domainObject = new Login();

		assertEquals(AbstractDomainObject.NOT_YET_SAVED_ID, domainObject.getId());
		assertTrue(domainObject.getVersion() < 0);
		assertNull(domainObject.getUpdatedAt());
		assertNull(domainObject.getCreatedAt());
	}


	@Ignore("Please make this run at some point later")
	// FIXME Update and Refresh
	public void testTimestamps() {
		domainObject = factory.getLogin();
		hibernateTemplate.persist(domainObject);

		assertNotNull(domainObject.getCreatedAt());
		assertNotNull(domainObject.getUpdatedAt());

		domainObject.setUsername("hello@world.com");
		hibernateTemplate.persist(domainObject);

		assertTrue(domainObject.getCreatedAt().before(domainObject.getUpdatedAt()));
	}

	@Test
	public void testSave(){
		domainObject = factory.getLogin();
		hibernateTemplate.persist(domainObject);

		assertNotSame(AbstractDomainObject.NOT_YET_SAVED_ID, domainObject.getId());
		assertTrue(domainObject.getId() > 0);

		assertEquals(0, domainObject.getVersion());
	}

	// TODO Some hibernate problem, should be fixed
	@Test
	public void testOptimisticLocking() {
		hibernateTemplate.save(domainObject);
		int version = domainObject.getVersion();
		Login v1 = (Login) hibernateTemplate.get(Login.class, domainObject.getId());
		Login v2 = (Login) hibernateTemplate.get(Login.class, domainObject.getId());
		
		v1.setPassword("Aenderung$1");
		hibernateTemplate.update(v1);
		assertTrue(version < v1.getId());
		//v2.setPassword("Aenderung$2");

		try {
			hibernateTemplate.update(v2);
			fail("Should fail because of Version Conflicts");
		} catch (HibernateOptimisticLockingFailureException e) {
			hibernateTemplate.evict(v2);
		} catch (org.hibernate.StaleObjectStateException e){
			hibernateTemplate.evict(v2);

		}

		Login v3 = (Login) hibernateTemplate.get(Login.class, domainObject.getId());
		assertEquals(v1.getPassword(), v3.getPassword());
		v2 = (Login) hibernateTemplate.get(Login.class, domainObject.getId());
//		hibernateTemplate.refresh(v2);
		v2.setPassword("Aenderung$2");
		hibernateTemplate.saveOrUpdate(v2);
		Login v4 = (Login) hibernateTemplate.get(Login.class, domainObject.getId());
		assertEquals(v2.getPassword(), v4.getPassword());
	}
}
