package de.randi2.dao;

import de.randi2.model.Trial;

public class TrialDaoHibernate extends AbstractDaoHibernate<Trial> implements TrialDao {

	@Override
	public Class<Trial> getModelClass() {
		return Trial.class;
	}


}
