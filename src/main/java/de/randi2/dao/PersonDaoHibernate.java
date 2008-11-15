package de.randi2.dao;

import de.randi2.model.Person;

public class PersonDaoHibernate extends AbstractDaoHibernate<Person> implements
		PersonDao {

	@Override
	public Class<Person> getModelClass() {
		return Person.class;
	}

}
