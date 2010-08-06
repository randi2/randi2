package de.randi2.core.integration.transactions;

import de.randi2.dao.PersonDao;
import de.randi2.model.Person;


public class TransactionalPersonDaoTest extends AbstractTransactionalTest<PersonDao,Person> {

	
	@Override
	protected void init() {
		try {
			databaseUtil.setUpDatabaseEmpty();
		} catch (Exception e) {
			e.printStackTrace();
		}
		dao = (PersonDao) applicationContext.getBean("personDao");
		object = factory.getPerson();
	}

}
