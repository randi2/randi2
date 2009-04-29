package de.randi2.transactions;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import de.randi2.dao.RoleDao;
import de.randi2.model.Role;
import de.randi2.test.utility.TestStringUtil;

public class TransactionalRoleTest extends
		AbstractTransactionalTest<RoleDao, Role> {

	@Autowired TestStringUtil stringUtil;
	@Autowired SessionFactory sessionFactory;
	@Override
	protected void init() {
		dao = (RoleDao)applicationContext.getBean("roleDAO");
		object = new Role(stringUtil.getWithLength(10), false, true,
				false, true, false, false, true, true, false, true, false, true,
				false, true, false, false, true, false, true, false, true, false,
				false, false, false, false, false, false, null);
		
	}

}
