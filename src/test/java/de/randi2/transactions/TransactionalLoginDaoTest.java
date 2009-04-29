package de.randi2.transactions;


import de.randi2.dao.LoginDao;
import de.randi2.model.Login;


public class TransactionalLoginDaoTest extends
		AbstractTransactionalTest<LoginDao, Login> {

	@Override
	protected void init() {
		dao = (LoginDao) applicationContext.getBean("loginDAO");
		object = factory.getLogin();
	}
	
}
