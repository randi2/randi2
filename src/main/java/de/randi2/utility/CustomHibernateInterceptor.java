package de.randi2.utility;

import org.hibernate.EmptyInterceptor;
import org.springframework.aop.support.AopUtils;

public class CustomHibernateInterceptor extends EmptyInterceptor{

	private static final long serialVersionUID = 8493168220915820397L;

	@Override
	public String getEntityName(Object object) {
		return AopUtils.getTargetClass(object).getName();	
	}
}
