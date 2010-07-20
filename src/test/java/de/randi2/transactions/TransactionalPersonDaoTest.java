package de.randi2.transactions;

import org.springframework.beans.factory.annotation.Autowired;

import de.randi2.dao.PersonDao;
import de.randi2.model.Person;
import de.randi2.utility.InitializeDatabaseUtil;


public class TransactionalPersonDaoTest extends AbstractTransactionalTest<PersonDao,Person> {

	@Autowired private InitializeDatabaseUtil databaseUtil;
	
	@Override
	protected void init() {
		try {
			databaseUtil.setUpDatabaseFull();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		dao = (PersonDao) applicationContext.getBean("personDao");
		object = factory.getPerson();
	}

}
