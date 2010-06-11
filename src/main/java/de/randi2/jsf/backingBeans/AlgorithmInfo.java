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
package de.randi2.jsf.backingBeans;

import de.randi2.jsf.controllerBeans.LoginHandler;
import de.randi2.jsf.wrappers.CriterionWrapper;
import de.randi2.model.randomization.BlockRandomizationConfig;
import de.randi2.model.randomization.MinimizationConfig;
import de.randi2.model.randomization.UrnDesignConfig;
import de.randi2.model.criteria.AbstractCriterion;
import de.randi2.model.criteria.constraints.AbstractConstraint;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * JSF backing bean which provieds some information about current trials
 * randomization algorithm. Created by IntelliJ IDEA. User: Lukasz Plotnicki
 * <l.plotnicki@dkfz.de> Date: 02.10.2009 Time: 15:13:16
 */
public class AlgorithmInfo {

	@Setter
	private Randi2Page randi2Page;

	@Setter
	private LoginHandler loginHandler;

	public String getAlgName() {
		ResourceBundle bundle = ResourceBundle
				.getBundle("de.randi2.jsf.i18n.algorithms", loginHandler
						.getChosenLocale());
		return bundle.getString(randi2Page.getCurrentTrial()
				.getRandomizationConfiguration().getClass().getCanonicalName()
				+ ".name");
	}

	public String getFurtherDetails() {
		StringBuffer furtherDetails = new StringBuffer();
		ResourceBundle bundle = ResourceBundle.getBundle(
				"de.randi2.jsf.i18n.labels", loginHandler.getChosenLocale());
		if (BlockRandomizationConfig.class.isInstance(randi2Page
				.getCurrentTrial().getRandomizationConfiguration())) {
			BlockRandomizationConfig conf = BlockRandomizationConfig.class
					.cast(randi2Page.getCurrentTrial()
							.getRandomizationConfiguration());
			furtherDetails.append("<b>");
			furtherDetails.append(bundle
					.getString("pages.blockR.variableBSize"));
			furtherDetails.append("</b> ");
			furtherDetails.append(conf.isVariableBlockSize());
			furtherDetails.append("<br//>");
			if (conf.isVariableBlockSize()) {
				furtherDetails.append("<b>");
				furtherDetails.append(bundle
						.getString("pages.blockR.minBlockSize"));
				furtherDetails.append("</b> ");
				furtherDetails.append(conf.getMinimum());
				furtherDetails.append("<br//>");
				furtherDetails.append("<b>");
				furtherDetails.append(bundle
						.getString("pages.blockR.maxBlockSize"));
				furtherDetails.append("</b> ");
				furtherDetails.append(conf.getMaximum());
				furtherDetails.append("<br//>");
			} else {
				furtherDetails.append("<b>");
				furtherDetails.append(bundle
						.getString("pages.blockR.blockSize"));
				furtherDetails.append("</b> ");
				furtherDetails.append(conf.getMinimum());
				furtherDetails.append("<br//>");
			}
		} else if (UrnDesignConfig.class.isInstance(randi2Page
				.getCurrentTrial().getRandomizationConfiguration())) {
			UrnDesignConfig conf = UrnDesignConfig.class.cast(randi2Page
					.getCurrentTrial().getRandomizationConfiguration());
			furtherDetails.append("<b>");
			furtherDetails.append(bundle.getString("pages.urnR.initialCount"));
			furtherDetails.append("</b> ");
			furtherDetails.append(conf.getInitializeCountBalls());
			furtherDetails.append("<br//>");
			furtherDetails.append("<b>");
			furtherDetails.append(bundle.getString("pages.urnR.replacedBalls"));
			furtherDetails.append("</b> ");
			furtherDetails.append(conf.getCountReplacedBalls());
			furtherDetails.append("<br//>");
		} else if (MinimizationConfig.class.isInstance(randi2Page
				.getCurrentTrial().getRandomizationConfiguration())) {
			MinimizationConfig conf = MinimizationConfig.class.cast(randi2Page
					.getCurrentTrial().getRandomizationConfiguration());
			furtherDetails.append("<b>");
			furtherDetails
					.append(bundle.getString("pages.minimization.pvalue"));
			furtherDetails.append("</b> ");
			furtherDetails.append(conf.getP());
			furtherDetails.append("<br//>");
		} else {
			furtherDetails.append("--");
		}
		return furtherDetails.toString();
	}

	/**
	 * Specifies if the algorithm is stratified or not.
	 * 
	 * @return
	 */
	public boolean isStratified() {
		boolean t = isStrataFactorsDefined();
		if (t)
			return t;
		else
			return randi2Page.getCurrentTrial().isStratifyTrialSite();
	}

	/**
	 * Checks if any strata factors are defined.
	 * 
	 * @return
	 */
	public boolean isStrataFactorsDefined() {

		for (AbstractCriterion<?, ?> c : randi2Page.getCurrentTrial()
				.getCriteria()) {
			if (c.getStrata() != null) {
				if (c.getStrata().size() > 0)
					return true;
			}
		}
		return false;
	}

	private List<CriterionWrapper<Serializable>> strata;

	@SuppressWarnings("unchecked")
	public List<CriterionWrapper<Serializable>> getStrata() {
		strata = new ArrayList<CriterionWrapper<Serializable>>();
		for (AbstractCriterion<? extends Serializable, ? extends AbstractConstraint<? extends Serializable>> c : randi2Page
				.getCurrentTrial().getCriteria()) {
			/*
			 * Check if the criterion is a defined strata factor and if so wrapp
			 * it and add to the collection
			 */
			if (c.getStrata() != null && !c.getStrata().isEmpty())
				strata.add(new CriterionWrapper<Serializable>(
						(AbstractCriterion<Serializable, ?>) c));
		}
		return strata;
	}

}
