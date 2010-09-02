/* 
 * (c) 2008- RANDI2 Core Development Team
 * 
 * This file is part of RANDI2.
 * 
 * RANDI2 is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * RANDI2 is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * RANDI2. If not, see <http://www.gnu.org/licenses/>.
 */
package de.randi2.dao;

import java.util.List;

import org.springframework.stereotype.Service;

import de.randi2.model.AbstractDomainObject;
import de.randi2.model.Login;
import de.randi2.model.Trial;
import de.randi2.model.TrialSubject;
import de.randi2.model.randomization.Block;
import de.randi2.model.randomization.BlockRandomizationConfig;
import de.randi2.model.randomization.BlockRandomizationTempData;
import de.randi2.model.randomization.Urn;
import de.randi2.model.randomization.UrnDesignConfig;
import de.randi2.model.randomization.UrnDesignTempData;

/**
 * The Class TrialDaoHibernate.
 */
@Service("trialDao")
public class TrialDaoHibernate extends AbstractDaoHibernate<Trial> implements
		TrialDao {

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.randi2.dao.AbstractDaoHibernate#getModelClass()
	 */
	@Override
	public Class<Trial> getModelClass() {
		return Trial.class;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seede.randi2.dao.AbstractDaoHibernate#update(de.randi2.model.
	 * AbstractDomainObject)
	 */
	@Override
	public Trial update(Trial object) {
		if (object.getRandomizationConfiguration() instanceof BlockRandomizationConfig) {
			for (String s : ((BlockRandomizationTempData) ((BlockRandomizationConfig) object
					.getRandomizationConfiguration()).getTempData())
					.getBlocks().keySet()) {
				Block b = ((BlockRandomizationTempData) ((BlockRandomizationConfig) object
						.getRandomizationConfiguration()).getTempData())
						.getBlocks().get(s);
				if (b != null
						&& b.getId() == AbstractDomainObject.NOT_YET_SAVED_ID) {
					entityManager.persist(b);
				}

			}
		} else if (object.getRandomizationConfiguration() instanceof UrnDesignConfig) {
			for (String s : ((UrnDesignTempData) ((UrnDesignConfig) object
					.getRandomizationConfiguration()).getTempData()).getUrns()
					.keySet()) {
				Urn u = ((UrnDesignTempData) ((UrnDesignConfig) object
						.getRandomizationConfiguration()).getTempData())
						.getUrns().get(s);
				if (u != null
						&& u.getId() == AbstractDomainObject.NOT_YET_SAVED_ID) {
					entityManager.persist(u);
				}

			}
		}

		return super.update(object);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.randi2.dao.TrialDao#getSubjects(de.randi2.model.Login)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<TrialSubject> getSubjects(Trial trial, Login investigator) {
		return entityManager.createNamedQuery(
				"trialSubject.specificInvestigator").setParameter(1, trial)
				.setParameter(1, investigator).getResultList();
	}

}
