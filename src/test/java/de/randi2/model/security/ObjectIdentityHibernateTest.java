package de.randi2.model.security;

import static junit.framework.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import de.randi2.model.Login;
import de.randi2.model.security.ObjectIdentityHibernate;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/META-INF/spring-test.xml" })
public class ObjectIdentityHibernateTest {

	@Autowired
	private HibernateTemplate hibernateTemplate;

	@Before
	public void setUp() {

	}

	@Test
	public void testConstructor() {
		ObjectIdentityHibernate objectId = new ObjectIdentityHibernate();
		assertTrue(objectId.getId() < 1);
		assertNull(objectId.getJavaType());
		assertNull(objectId.getIdentifier());
		objectId = new ObjectIdentityHibernate(Login.class, 10);
		assertTrue(objectId.getId() < 1);
		assertTrue(objectId.getJavaType().equals(Login.class));
		assertTrue(objectId.getIdentifier().equals(10l));
	}

	@Test
	public void testJavaType() {
		ObjectIdentityHibernate objectId = new ObjectIdentityHibernate();
		assertNull(objectId.getJavaType());
		objectId.setJavaType(Login.class);
		assertEquals(Login.class, objectId.getJavaType());
	}

	@Test
	public void testIdentifier() {
		ObjectIdentityHibernate objectId = new ObjectIdentityHibernate();
		assertNull(objectId.getIdentifier());
		objectId.setIdentifier(10l);
		assertTrue(objectId.getIdentifier().equals(10l));
	}

	@Test
	public void databaseIntegrationTest() {
		ObjectIdentityHibernate objectId = new ObjectIdentityHibernate(Login.class, 10);
		hibernateTemplate.persist(objectId);
		assertTrue(objectId.getId()>0);
		ObjectIdentityHibernate dbObjectId = (ObjectIdentityHibernate) hibernateTemplate.get(ObjectIdentityHibernate.class, objectId.getId());
		assertEquals(objectId.getId(), dbObjectId.getId());
		assertEquals(objectId.getIdentifier(), dbObjectId.getIdentifier());
		assertEquals(objectId.getJavaType(), dbObjectId.getJavaType());
	}
}
