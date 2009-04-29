package de.randi2.dao;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import de.randi2.dao.TrialSiteDao;
import de.randi2.model.AbstractDomainObject;
import de.randi2.model.TrialSite;
import de.randi2.test.utility.DomainObjectFactory;
import de.randi2.test.utility.TestStringUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/META-INF/spring-test.xml" })
public class TrialSiteDaoTest {


	@Autowired
	private TrialSiteDao trialSiteDao;
	@Autowired
	private DomainObjectFactory factory;
@Autowired
private HibernateTemplate hibernateTemplate;
	
	@Test
	public void testGetAll(){
		for (int i=0;i<100;i++){
			TrialSite c = factory.getTrialSite();
			hibernateTemplate.save(c.getContactPerson());
			trialSiteDao.create(c);
		}
		assertTrue(trialSiteDao.getAll().size()>=100);
	}
	@Test
	public void testGetName(){
		TrialSite c = factory.getTrialSite();
		hibernateTemplate.save(c.getContactPerson());
		trialSiteDao.create(c);
		assertTrue(c.getId()!=AbstractDomainObject.NOT_YET_SAVED_ID);
		
		TrialSite c1 = trialSiteDao.get(c.getName());
		
		assertEquals(c.getId(), c1.getId());
		assertEquals(c.getName(), c1.getName());
	}
}
