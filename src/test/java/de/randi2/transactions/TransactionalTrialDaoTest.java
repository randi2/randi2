package de.randi2.transactions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;

import de.randi2.dao.TrialDao;
import de.randi2.model.Trial;

public class TransactionalTrialDaoTest extends
		AbstractTransactionalTest<TrialDao, Trial> {

	@Autowired private HibernateTemplate template;
	@Override
	protected void init() {
		dao = (TrialDao)applicationContext.getBean("trialDao");
		object = factory.getTrial();
		template.save(object.getLeadingSite().getContactPerson());
		template.save(object.getLeadingSite());
		template.save(object.getSponsorInvestigator());
		
	}
}
