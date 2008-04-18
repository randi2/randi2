package de.randi2.dao;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import de.randi2.model.Trial;

public class TrialDaoHibernate extends AbstractDaoHibernate<Trial> implements TrialDao {
	
	public Trial get(long id) {
		return (Trial) template.get(Trial.class, id);
	}

	
	public void save(Trial trial) {
		template.saveOrUpdate(trial);
	}

}
