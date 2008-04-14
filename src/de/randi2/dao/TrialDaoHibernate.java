package de.randi2.dao;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import de.randi2.model.Trial;

public class TrialDaoHibernate extends HibernateDaoSupport implements TrialDao {

	public Trial get(Long id) {
		return (Trial) getSession().get(Trial.class, id);
	}

	public void save(Trial trial) {
		getSession().saveOrUpdate(trial);
	}

}
