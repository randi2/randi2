package com.myicetest.dao;

import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;

public abstract class AbstractDaoHibernate<E extends Object> implements AbstractDao<E>{
	protected HibernateTemplate template;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.template = new HibernateTemplate(sessionFactory);
	}
}
