package de.randi2.dao;

import de.randi2.model.AbstractDomainObject;
import de.randi2.model.Trial;
import de.randi2.model.randomization.Block;
import de.randi2.model.randomization.BlockRandomizationConfig;

public class TrialDaoHibernate extends AbstractDaoHibernate<Trial> implements TrialDao {

	@Override
	public Class<Trial> getModelClass() {
		return Trial.class;
	}

	@Override
	public Trial update(Trial object) {
		if(object.getRandomizationConfiguration() instanceof BlockRandomizationConfig){
			for(String s : ((BlockRandomizationConfig)object.getRandomizationConfiguration()).getTempData().getBlocks().keySet()){
				Block b = ((BlockRandomizationConfig)object.getRandomizationConfiguration()).getTempData().getBlocks().get(s);
				if(b != null && b.getId() == AbstractDomainObject.NOT_YET_SAVED_ID){
					sessionFactory.getCurrentSession().persist(b);
				}
				
			}
		}
		
		return super.update(object);
	}

}
