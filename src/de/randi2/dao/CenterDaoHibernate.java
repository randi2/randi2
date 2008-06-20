package de.randi2.dao;

import de.randi2.model.Center;

public class CenterDaoHibernate extends AbstractDaoHibernate<Center> implements CenterDao {

	@Override
	public Class<Center> getModelClass() {
		return Center.class;
	}

}
