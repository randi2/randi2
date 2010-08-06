package de.randi2.core.integration.dao;

import static de.randi2.testUtility.utility.RANDI2Assert.assertSaved;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import de.randi2.dao.RoleDao;
import de.randi2.model.Role;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/META-INF/spring-test.xml"})
@Transactional
public class RoleDaoTest extends AbstractDaoTest{

	
	@Autowired
	private RoleDao roleDao;
	
	@Test
	public void testGetAll(){
		assertTrue(roleDao.getAll().size()>=0);
		int size = roleDao.getAll().size();
		for(int i = 0;i<10 ;i++){
			Role r = new Role();
			r.setName("name" + i);
			roleDao.create(r);
		}
		assertEquals(size+10, roleDao.getAll().size());
	}
	
	
	@Test
	public void createAndSaveTest() {
		Role r = new Role();
		r.setName("name");
		roleDao.create(r);
		assertSaved(r);

		assertNotNull(roleDao.get(r.getId()));
		assertEquals("name", roleDao.get(r.getId()).getName());

	}

}
