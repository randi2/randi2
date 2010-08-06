package de.randi2.core.integration.transactions;

import de.randi2.dao.TrialSiteDao;
import de.randi2.model.TrialSite;

public class TransactionalTrialSiteDao extends
		AbstractTransactionalTest<TrialSiteDao, TrialSite> {

	@Override
	protected void init() {
		dao = (TrialSiteDao)applicationContext.getBean("trialSiteDao");
		object = factory.getTrialSite();
	}

}
