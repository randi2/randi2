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
package de.randi2.jsf.supportBeans;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

import lombok.Getter;
import lombok.Setter;

import com.icesoft.faces.component.outputchart.OutputChart;
import com.icesoft.faces.context.effects.Effect;
import com.icesoft.faces.context.effects.Highlight;

import de.randi2.jsf.controllerBeans.TrialHandler;
import de.randi2.model.randomization.ChartData;
import de.randi2.services.ChartsService;

/**
 * Backing bean for the charts.
 * @author Lukasz Plotnicki <l.plotnicki@dkfz.de>
 *
 */
@ManagedBean(name="charts")
@RequestScoped
public class Charts {
	
	@Getter
	List<SelectItem> chartTypes;
	@ManagedProperty(value="#{trialHandler}")
	@Setter
	private TrialHandler trialHandler;
	@ManagedProperty(value="#{chartsService}")
	@Setter
	private ChartsService service;
	
	@Getter @Setter
	private String rChartType = "barclustered";

	@Getter @Setter
	private Map<String, String> clickedValues;
	@Getter @Setter
	private Map<String, Effect> effects;

	public Charts(){
		chartTypes = new ArrayList<SelectItem>();
		chartTypes.add(new SelectItem("barclustered", "Bars (clustered)"));
		chartTypes.add(new SelectItem("line", "Lines"));
		chartTypes.add(new SelectItem("area", "Area"));
		clickedValues = new HashMap<String, String>();
		effects = new HashMap<String, Effect>();
		clickedValues.put("armChart", "not selected");
		Effect e1 = new Highlight("#fda505");
	    e1.setFired(true);
	    effects.put("armChart", e1);
		clickedValues.put("recruitmentChart", "not selected");
		Effect e2 = new Highlight("#fda505");
	    e2.setFired(true);
	    effects.put("recruitmentChart", e2);
		clickedValues.put("strataChart", "not selected");
		Effect e3 = new Highlight("#fda505");
	    e3.setFired(true);
	    effects.put("strataChart", e3);
		clickedValues.put("trialSiteChart", "not selected");
		Effect e4 = new Highlight("#fda505");
	    e4.setFired(true);
	    effects.put("trialSiteChart", e4);
	}
	
	/**
	 * Action for the user interaction with the chart.
	 * 
	 * @param event
	 *            - JSF event
	 */
	public void action(ActionEvent event) {
		if (event.getSource() instanceof OutputChart) {
			OutputChart chart = (OutputChart) event.getSource();
			clickedValues.put(chart.getId(), "not selected");
			if (chart.getClickedImageMapArea().getXAxisLabel() != null) {
				clickedValues.put(chart.getId(), "("+chart.getClickedImageMapArea().getXAxisLabel()
						+ "  |  " + chart.getClickedImageMapArea().getValue()+")");
				effects.get(chart.getId()).setFired(false);
			}else{
				 chart.getClickedImageMapArea().getValue();
			}
		}

	}

	public ChartData getRChartData() {
		if (trialHandler.getCurrentObject() != null)
			return service.generateRecruitmentChart(trialHandler.getCurrentObject());
		return null;
	}
	
	public ChartData getAChartData(){
		if (trialHandler.getCurrentObject() != null)
			return service.generateArmChart(trialHandler.getCurrentObject());
		return null;
	}
	
	public ChartData getRTrialSiteChartData(){
		if (trialHandler.getCurrentObject() != null)
			return service.generateRecruitmentChartTrialSite(trialHandler.getCurrentObject());
		return null;
	}
	
	public ChartData getRFactorsChartData() {
		if (trialHandler.getCurrentObject() != null)
			return service.generateRecruitmentChartFactors(trialHandler.getCurrentObject());
		return null;
	}

}
