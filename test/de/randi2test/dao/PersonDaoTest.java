package de.randi2test.dao;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNotSame;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import de.randi2.dao.PersonDao;
import de.randi2.model.AbstractDomainObject;
import de.randi2.model.Person;
import de.randi2test.model.util.ObjectFactory;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/META-INF/spring-test.xml"})
@Transactional
public class PersonDaoTest {

	@Autowired private PersonDao dao;	
	@Autowired private ObjectFactory factory;
	
	@Test
	public void CreateAndSaveTest(){
		
		
		Person p = factory.getPerson();
		
		assertEquals(AbstractDomainObject.NOT_YET_SAVED_ID, p.getId());
		dao.save(p);
		assertNotSame(AbstractDomainObject.NOT_YET_SAVED_ID, p.getId());
		
		assertNotNull(dao.get(p.getId()));
		
		
	}
	
}
