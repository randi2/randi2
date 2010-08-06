package de.randi2.core.integration.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import de.randi2.dao.TrialSiteDao;
import de.randi2.model.AbstractDomainObject;
import de.randi2.model.TrialSite;
import de.randi2.testUtility.utility.DomainObjectFactory;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/META-INF/spring-test.xml" })
@Transactional
public class TrialSiteDaoTest extends AbstractDaoTest{


	@Autowired
	private TrialSiteDao trialSiteDao;
	@Autowired
	private DomainObjectFactory factory;
	
	@Test
	public void testGetAll(){
		for (int i=0;i<100;i++){
			TrialSite c = factory.getTrialSite();
			trialSiteDao.create(c);
		}
		assertTrue(trialSiteDao.getAll().size()>=100);
	}
	@Test
	public void testGetName(){
		TrialSite c = factory.getTrialSite();
		trialSiteDao.create(c);
		assertTrue(c.getId()!=AbstractDomainObject.NOT_YET_SAVED_ID);
		
		TrialSite c1 = trialSiteDao.get(c.getName());
		
		assertEquals(c.getId(), c1.getId());
		assertEquals(c.getName(), c1.getName());
	}
}
