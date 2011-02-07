package de.randi2.core.integration.transactions;

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
		entityManager.getTransaction().begin();
		entityManager.persist(object.getLeadingSite().getContactPerson());
		entityManager.persist(object.getLeadingSite());
		entityManager.persist(object.getSponsorInvestigator());
		entityManager.getTransaction().commit();
		
	}
}
