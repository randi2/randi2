package de.randi2.dao;

import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import de.randi2.model.AbstractDomainObject;
import de.randi2.model.TrialSite;

public class TrialSiteDaoHibernate extends AbstractDaoHibernate<TrialSite> implements TrialSiteDao {

	@Override
	public Class<TrialSite> getModelClass() {
		return TrialSite.class;
	}
	
	

	@Override
	@SuppressWarnings("unchecked")
	@Transactional(propagation=Propagation.SUPPORTS)
	public TrialSite get(String name) {
		String query = "from de.randi2.model.TrialSite trialSite where "
			+ "trialSite.name =?";
		List<TrialSite>  list =(List) sessionFactory.getCurrentSession().createQuery(query).setParameter(0, name).list();
		if (list.size() ==1)	return list.get(0);
		else return null;
	}
	
	

	
}
