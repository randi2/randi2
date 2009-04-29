package de.randi2.transactions;

import de.randi2.dao.PersonDao;
import de.randi2.model.Person;


public class TransactionalPersonDaoTest extends AbstractTransactionalTest<PersonDao,Person> {

	@Override
	protected void init() {
		dao = (PersonDao) applicationContext.getBean("personDAO");
		object = factory.getPerson();
	}

}
