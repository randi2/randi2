package de.randi2.dao;

import java.util.List;

import de.randi2.model.Login;

public class LoginDaoHibernate extends AbstractDaoHibernate<Login> implements LoginDao{

	@SuppressWarnings("unchecked")
	public Login get(String username) {
		String query = "from de.randi2.model.Login login where "
			+ "login.username =?";
	 
		List<Login>  loginList =(List) template.find(query, username);
		if (loginList.size() ==1)	return loginList.get(0);
		else return null;
	}

	@Override
	public Class<Login> getModelClass() {
		return Login.class;
	}

}
