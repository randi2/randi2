package de.randi2.dao;

import org.springframework.orm.ObjectRetrievalFailureException;

import de.randi2.model.Trial;

public class TrialDaoHibernate extends AbstractDaoHibernate<Trial> implements TrialDao {
	
	public Trial get(long id) {
		Trial t = (Trial) template.get(Trial.class, id);
		if(t == null){
			throw new ObjectRetrievalFailureException(Trial.class, id);
		}
		return t;
	}

	
	public void save(Trial trial) {
		template.saveOrUpdate(trial);
	}

}
