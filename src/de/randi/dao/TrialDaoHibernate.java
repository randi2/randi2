package de.randi2.dao;

import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import de.randi2.model.Trial;

public class TrialDaoHibernate extends AbstractDaoHibernate<Trial> implements TrialDao {
	
	public Trial get(long id) {
		Trial t = (Trial) template.get(Trial.class, id);
		if(t == null){
			throw new ObjectRetrievalFailureException(Trial.class, id);
		}
		return t;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void save(Trial trial) {
		template.saveOrUpdate(trial);
	}

}
