package de.randi2.jsf.backingBeans;

import de.randi2.jsf.controllerBeans.TrialHandler;
import de.randi2.jsf.wrappers.CriterionWrapper;
import de.randi2.model.Trial;
import de.randi2.model.criteria.AbstractCriterion;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: Lukasz Plotnicki <l.plotnicki@dkfz.de>
 * Date: 02.10.2009
 * Time: 15:13:16
 */
public class AlgorithmInfo {

    @Setter
    private Randi2Page randi2Page;

    public String getAlgName(){
        return randi2Page.getCurrentTrial().getRandomizationConfiguration().getAlgorithm().getClass().getSimpleName();
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
