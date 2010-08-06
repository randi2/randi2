package de.randi2.core.integration.dao;

import static de.randi2.testUtility.utility.RANDI2Assert.assertNotSaved;
import static de.randi2.testUtility.utility.RANDI2Assert.assertSaved;
import static junit.framework.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import de.randi2.dao.TrialDao;
import de.randi2.model.Trial;
import de.randi2.testUtility.utility.DomainObjectFactory;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/META-INF/spring-test.xml"})
@Transactional
public class TrialDaoTest extends AbstractDaoTest{

	
	@Autowired	private TrialDao dao;
	@Autowired private DomainObjectFactory factory;

	@Test
	public void testCreateAndSave() {
		Trial t1 = factory.getTrial();
		sessionFactory.getCurrentSession().save(t1.getLeadingSite().getContactPerson());
		sessionFactory.getCurrentSession().save(t1.getLeadingSite());
		sessionFactory.getCurrentSession().save(t1.getSponsorInvestigator());
		assertNotSaved(t1);
		dao.create(t1);
		assertSaved(t1);

		assertNotNull(dao.get(t1.getId()));		
		
	}

}
