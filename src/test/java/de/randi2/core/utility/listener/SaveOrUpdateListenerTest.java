package de.randi2.core.utility.listener;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.fail;

import java.util.GregorianCalendar;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import de.randi2.model.AbstractDomainObject;
import de.randi2.model.Login;
import de.randi2.testUtility.utility.DomainObjectFactory;

//import static junit.framework.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:/META-INF/service-test.xml",
		"classpath:/META-INF/subconfig/test.xml" })
public class SaveOrUpdateListenerTest {

	private EntityManager entityManager;

	@PersistenceContext
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Autowired
	private DomainObjectFactory factory;


	@Test
	@Transactional
	public void onPersist() {
		AbstractDomainObject object = factory.getPerson();
		assertNull(object.getCreatedAt());
		assertNull(object.getUpdatedAt());
		entityManager.persist(object);
		entityManager.flush();
		assertNotNull(object.getCreatedAt());
		assertNotNull(object.getUpdatedAt());
	}

	@Test
	public void onPersistWithCascade() {
		Login object = factory.getLogin();
		assertNull(object.getCreatedAt());
		assertNull(object.getUpdatedAt());
		assertNull(object.getPerson().getCreatedAt());
		assertNull(object.getPerson().getUpdatedAt());
		entityManager.persist(object);
		assertNotNull(object.getCreatedAt());
		assertNotNull(object.getUpdatedAt());
		assertNotNull(object.getPerson().getCreatedAt());
		assertNotNull(object.getPerson().getUpdatedAt());
	}

	@Test
	@Transactional
	public void onMerge() {
		AbstractDomainObject object = factory.getPerson();
		assertNull(object.getCreatedAt());
		assertNull(object.getUpdatedAt());
		entityManager.persist(object);
		assertNotNull(object.getCreatedAt());
		assertNotNull(object.getUpdatedAt());
		entityManager.flush();
		GregorianCalendar create = object.getCreatedAt();
		GregorianCalendar update = (GregorianCalendar)object.getUpdatedAt().clone();
		
		try{
			Thread.sleep(1000);
			object = entityManager.merge(object);
			entityManager.flush();
			assertEquals(create, object.getCreatedAt());
			assertEquals(0, update.compareTo(object.getUpdatedAt()));
		}catch (Exception e) {
			fail(e.getMessage());
		}
	
	}

	@Test
	@Transactional
	public void onMergewithCascade() {
		Login object = factory.getLogin();
		assertNull(object.getCreatedAt());
		assertNull(object.getUpdatedAt());
		assertNull(object.getPerson().getCreatedAt());
		assertNull(object.getPerson().getUpdatedAt());
		entityManager.persist(object);
		assertNotNull(object.getCreatedAt());
		assertNotNull(object.getUpdatedAt());
		assertNotNull(object.getPerson().getCreatedAt());
		assertNotNull(object.getPerson().getUpdatedAt());

		GregorianCalendar createL = new GregorianCalendar();
		createL.setTimeInMillis(object.getCreatedAt().getTimeInMillis());
		GregorianCalendar updateL = new GregorianCalendar();
		updateL.setTimeInMillis(object.getUpdatedAt().getTimeInMillis());
		GregorianCalendar createP = new GregorianCalendar();
		createP.setTimeInMillis(object.getPerson().getCreatedAt().getTimeInMillis());
		GregorianCalendar updateP = new GregorianCalendar();
		updateP.setTimeInMillis(object.getPerson().getUpdatedAt().getTimeInMillis());
		
		entityManager.flush();
		try{
		Thread.sleep(1000);
		object = entityManager.merge(object);
		entityManager.flush();
		assertEquals(createL, object.getCreatedAt());
		assertEquals(updateL, object.getUpdatedAt());
		assertEquals(createP, object.getPerson().getCreatedAt());
		assertEquals(updateP,object.getPerson().getUpdatedAt());
		}catch (Exception e) {
			fail(e.getMessage());
		}
		
		object.setPerson(factory.getPerson());

		assertNull(object.getPerson().getCreatedAt());
		assertNull(object.getPerson().getUpdatedAt());
		entityManager.merge(object);
		assertNotNull(object.getPerson().getCreatedAt());
		assertNotNull(object.getPerson().getUpdatedAt());
	}
	
	
	@Test
	@Transactional
	public void onSaveOrUpdate() {
		Login object = factory.getLogin();
		assertNull(object.getCreatedAt());
		assertNull(object.getUpdatedAt());
		assertNull(object.getPerson().getCreatedAt());
		assertNull(object.getPerson().getUpdatedAt());
		entityManager.persist(object);
		assertNotNull(object.getCreatedAt());
		assertNotNull(object.getUpdatedAt());
		assertNotNull(object.getPerson().getCreatedAt());
		assertNotNull(object.getPerson().getUpdatedAt());
		entityManager.flush();
		GregorianCalendar createL = new GregorianCalendar();
		createL.setTimeInMillis(object.getCreatedAt().getTimeInMillis());
		GregorianCalendar updateL = new GregorianCalendar();
		updateL.setTimeInMillis(object.getUpdatedAt().getTimeInMillis());
		GregorianCalendar createP = new GregorianCalendar();
		createP.setTimeInMillis(object.getPerson().getCreatedAt().getTimeInMillis());
		try{
		Thread.sleep(1000);
		object = entityManager.merge(object);
		entityManager.flush();
		assertEquals(createL, object.getCreatedAt());
		assertEquals(updateL,object.getUpdatedAt());
		assertEquals(createP, object.getPerson().getCreatedAt());
		}catch (Exception e) {
			fail(e.getMessage());
		}
		
		object.setPerson(factory.getPerson());

		assertNull(object.getPerson().getCreatedAt());
		assertNull(object.getPerson().getUpdatedAt());
		object = entityManager.merge(object);
		entityManager.flush();
		assertNotNull(object.getPerson().getCreatedAt());
		assertNotNull(object.getPerson().getUpdatedAt());
	}

}
