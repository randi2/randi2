package de.randi2.jsf.backingBeans;

import de.randi2.jsf.controllerBeans.TrialHandler;
import de.randi2.jsf.controllerBeans.LoginHandler;
import de.randi2.jsf.wrappers.CriterionWrapper;
import de.randi2.model.Trial;
import de.randi2.model.randomization.BlockRandomizationConfig;
import de.randi2.model.criteria.AbstractCriterion;
import lombok.Getter;
import lombok.Setter;

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
        for(AbstractCriterion c : randi2Page.getCurrentTrial().getCriteria()){
            if(c.getStrata().size()>0)
                return true;
        }
        return false;
    }

    private List<CriterionWrapper> strata;

    public List<CriterionWrapper> getStrata(){
        strata  = new ArrayList<CriterionWrapper>();
        for(AbstractCriterion c : randi2Page.getCurrentTrial().getCriteria()){
            strata.add(new CriterionWrapper(c));
        }
        return strata;
    }

}
