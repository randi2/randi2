package de.randi2.dao;

import de.randi2.model.Login;

public class LoginDaoHibernate extends AbstractDaoHibernate<Login> implements LoginDao{

	public Login get(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Class<Login> getModelClass() {
		return Login.class;
	}

}
