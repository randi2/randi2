package de.randi2.dao;

import static junit.framework.Assert.fail;

import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;

import de.randi2.utility.InitializeDatabaseUtil;

public abstract class AbstractDaoTest {

	
	@Autowired
	private InitializeDatabaseUtil databaseUtil;
	
	@Before
	public void setUp(){
		try {
			databaseUtil.setUpDatabase();
		} catch (Exception e) {
			fail();
		}
	}
}
