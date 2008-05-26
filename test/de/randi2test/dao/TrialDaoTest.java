package de.randi2test.dao;

import static de.randi2test.utility.RANDI2Assert.assertNotSaved;
import static de.randi2test.utility.RANDI2Assert.assertSaved;
import static junit.framework.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import de.randi2.dao.TrialDao;
import de.randi2.model.Trial;
import de.randi2test.utility.DomainObjectFactory;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/META-INF/spring-test.xml"})
@Transactional
public class TrialDaoTest {

	
	 
	
	@Autowired	private TrialDao dao;
	@Autowired private DomainObjectFactory factory;

	@Test
	public void testCreateAndSave() {
		Trial t1 = factory.getTrial();
		assertNotSaved(t1);
		dao.save(t1);
		assertSaved(t1);

		assertNotNull(dao.get(t1.getId()));		
		
	}

}
