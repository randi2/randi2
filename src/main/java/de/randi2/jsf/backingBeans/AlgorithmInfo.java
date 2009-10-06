package de.randi2.jsf.backingBeans;

import de.randi2.jsf.controllerBeans.LoginHandler;
import de.randi2.jsf.wrappers.CriterionWrapper;
import de.randi2.model.randomization.BlockRandomizationConfig;
import de.randi2.model.criteria.AbstractCriterion;
import de.randi2.model.criteria.constraints.AbstractConstraint;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Created by IntelliJ IDEA.
 * User: Lukasz Plotnicki <l.plotnicki@dkfz.de>
 * Date: 02.10.2009
 * Time: 15:13:16
 */
public class AlgorithmInfo {

    @Setter
    private Randi2Page randi2Page;

    @Setter
    private LoginHandler loginHandler;

    public String getAlgName(){
        ResourceBundle bundle = ResourceBundle.getBundle(
					"de.randi2.jsf.i18n.algorithms", loginHandler
							.getChosenLocale());
        return bundle.getString(randi2Page.getCurrentTrial().getRandomizationConfiguration().getClass().getCanonicalName()+".name");
    }

    public String getFurtherDetails(){
        if(BlockRandomizationConfig.class.isInstance(randi2Page.getCurrentTrial().getRandomizationConfiguration())){
           BlockRandomizationConfig conf = BlockRandomizationConfig.class.cast(randi2Page.getCurrentTrial().getRandomizationConfiguration());
           //TODO get all further configuration details and push it into one string
        }
        return "NEEDS TO BE IMPLEMENTED*";
    }

    public boolean isStratified(){
        for(AbstractCriterion<?,?> c : randi2Page.getCurrentTrial().getCriteria()){
            if(c.getStrata().size()>0)
                return true;
        }
//        randi2Page.getCurrentTrial().isStratifyTrialSite()
        return false;
    }

    private List<CriterionWrapper<Serializable>> strata;

    @SuppressWarnings("unchecked")
	public List<CriterionWrapper<Serializable>> getStrata(){
        strata  = new ArrayList<CriterionWrapper<Serializable>>();
        for(AbstractCriterion<? extends Serializable, ? extends AbstractConstraint<? extends Serializable>> c : randi2Page.getCurrentTrial().getCriteria()){
            strata.add(new CriterionWrapper<Serializable>((AbstractCriterion<Serializable, ?>) c));
        }
        return strata;
    }

}
