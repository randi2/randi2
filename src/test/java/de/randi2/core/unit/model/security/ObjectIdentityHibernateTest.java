package de.randi2.core.unit.model.security;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import de.randi2.model.Login;
import de.randi2.model.security.ObjectIdentityHibernate;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/META-INF/spring-test.xml" })
public class ObjectIdentityHibernateTest {


	private EntityManager entityManager;
	
	
	@PersistenceContext
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

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
		entityManager.persist(objectId);
		assertTrue(objectId.getId()>0);
		ObjectIdentityHibernate dbObjectId = entityManager.find(ObjectIdentityHibernate.class, objectId.getId());
		assertEquals(objectId.getId(), dbObjectId.getId());
		assertEquals(objectId.getIdentifier(), dbObjectId.getIdentifier());
		assertEquals(objectId.getType(), dbObjectId.getType());
	}
}
