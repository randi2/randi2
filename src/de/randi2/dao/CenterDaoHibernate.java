package de.randi2.dao;

import java.util.List;

import de.randi2.model.Center;
import de.randi2.model.Login;

public class CenterDaoHibernate extends AbstractDaoHibernate<Center> implements CenterDao {

	@Override
	public Class<Center> getModelClass() {
		return Center.class;
	}
	
	public List<Center> getAll(){
		return template.loadAll(Center.class);
	}

	@Override
	public Center get(String name) {
		String query = "from de.randi2.model.Center center where "
			+ "center.name =?";
	 
		List<Center>  list =(List) template.find(query, name);
		if (list.size() ==1)	return list.get(0);
		else return null;
	}

}
