package de.randi2.dao;

import java.util.List;

import de.randi2.model.Center;

public class CenterDaoHibernate extends AbstractDaoHibernate<Center> implements CenterDao {

	@Override
	public Class<Center> getModelClass() {
		return Center.class;
	}
	
	public List<Center> getAll(){
		return template.loadAll(Center.class);
	}

}
