package de.randi2.dao;

import static de.randi2.test.utility.RANDI2Assert.assertNotSaved;
import static de.randi2.test.utility.RANDI2Assert.assertSaved;
import static junit.framework.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import de.randi2.dao.TrialDao;
import de.randi2.model.Trial;
import de.randi2.test.utility.DomainObjectFactory;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/META-INF/spring-test.xml"})
@Transactional
public class TrialDaoTest {

	
	 
	@Autowired private HibernateTemplate template;
	@Autowired	private TrialDao dao;
	@Autowired private DomainObjectFactory factory;

	@Test
	public void testCreateAndSave() {
		Trial t1 = factory.getTrial();
		template.save(t1.getLeadingSite().getContactPerson());
		template.save(t1.getLeadingSite());
		template.save(t1.getSponsorInvestigator());
		assertNotSaved(t1);
		dao.create(t1);
		assertSaved(t1);

		assertNotNull(dao.get(t1.getId()));		
		
	}

}
