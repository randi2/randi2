package de.randi2.core.unit.model.security;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;

import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import de.randi2.model.Login;
import de.randi2.model.security.ObjectIdentityHibernate;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/META-INF/spring-test.xml" })
public class ObjectIdentityHibernateTest {


	@Autowired
	private SessionFactory sessionFactory;
	

	@Before
	public void setUp() {

	}

	@Test
	public void testConstructor() {
		ObjectIdentityHibernate objectId = new ObjectIdentityHibernate();
		assertTrue(objectId.getId() < 1);
		assertNull(objectId.getType());
		assertNull(objectId.getIdentifier());
		objectId = new ObjectIdentityHibernate(Login.class, 10);
		assertTrue(objectId.getId() < 1);
		assertTrue(objectId.getType().equals(Login.class.getCanonicalName()));
		assertTrue(objectId.getIdentifier().equals(10l));
	}

	@Test
	public void testJavaType() {
		ObjectIdentityHibernate objectId = new ObjectIdentityHibernate();
		assertNull(objectId.getType());
		objectId.setType(Login.class.getCanonicalName());
		assertEquals(Login.class.getCanonicalName(), objectId.getType());
	}

	@Test
	public void testIdentifier() {
		ObjectIdentityHibernate objectId = new ObjectIdentityHibernate();
		assertNull(objectId.getIdentifier());
		objectId.setIdentifier(10l);
		assertTrue(objectId.getIdentifier().equals(10l));
	}

	@Test
	@Transactional
	public void databaseIntegrationTest() {
		ObjectIdentityHibernate objectId = new ObjectIdentityHibernate(Login.class, 10);
		sessionFactory.getCurrentSession().persist(objectId);
		assertTrue(objectId.getId()>0);
		ObjectIdentityHibernate dbObjectId = (ObjectIdentityHibernate) sessionFactory.getCurrentSession().get(ObjectIdentityHibernate.class, objectId.getId());
		assertEquals(objectId.getId(), dbObjectId.getId());
		assertEquals(objectId.getIdentifier(), dbObjectId.getIdentifier());
		assertEquals(objectId.getType(), dbObjectId.getType());
	}
}
