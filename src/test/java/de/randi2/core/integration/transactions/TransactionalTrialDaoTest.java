package de.randi2.core.integration.transactions;

import org.hibernate.Transaction;
import org.hibernate.classic.Session;

import de.randi2.dao.TrialDao;
import de.randi2.model.Trial;

public class TransactionalTrialDaoTest extends
		AbstractTransactionalTest<TrialDao, Trial> {


	@Override
	protected void init() {
		try {
			databaseUtil.setUpDatabaseEmpty();
		} catch (Exception e) {
			e.printStackTrace();
		}
		dao = (TrialDao)applicationContext.getBean("trialDao");
		object = factory.getTrial();
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		session.save(object.getLeadingSite().getContactPerson());
		session.save(object.getLeadingSite());
		session.save(object.getSponsorInvestigator());
		transaction.commit();
		
	}
}
