package de.randi2.dao;

import java.util.List;

import de.randi2.model.TrialSite;
import de.randi2.model.Login;

public class TrialSiteDaoHibernate extends AbstractDaoHibernate<TrialSite> implements TrialSiteDao {

	@Override
	public Class<TrialSite> getModelClass() {
		return TrialSite.class;
	}
	
	public List<TrialSite> getAll(){
		return template.loadAll(TrialSite.class);
	}

	@Override
	public TrialSite get(String name) {
		String query = "from de.randi2.model.TrialSite trialSite where "
			+ "trialSite.name =?";
	 
		List<TrialSite>  list =(List) template.find(query, name);
		if (list.size() ==1)	return list.get(0);
		else return null;
	}

}
