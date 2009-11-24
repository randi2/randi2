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

import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

import lombok.Getter;
import lombok.Setter;

import com.icesoft.faces.component.outputchart.OutputChart;

import de.randi2.jsf.controllerBeans.TrialHandler;
import de.randi2.model.randomization.ChartData;
import de.randi2.services.ChartsService;

/**
 * Backing bean for the charts.
 * @author Lukasz Plotnicki <l.plotnicki@dkfz.de>
 *
 */
public class Charts {
	
	@Getter
	List<SelectItem> chartTypes;

	@Setter
	private TrialHandler trialHandler;

	@Setter
	private ChartsService service;
	
	@Getter @Setter
	private String rChartType = "barclustered";

	@Getter @Setter
	private Map<String, String> clickedValues;

	public Charts(){
		chartTypes = new ArrayList<SelectItem>();
		chartTypes.add(new SelectItem("barclustered", "Bars (clustered)"));
		chartTypes.add(new SelectItem("line", "Lines"));
		chartTypes.add(new SelectItem("area", "Area"));
		clickedValues = new HashMap<String, String>();
		clickedValues.put("armChart", "not selected");
		clickedValues.put("recruitmentChart", "not selected");
		clickedValues.put("strataChart", "not selected");
		clickedValues.put("trialSiteChart", "not selected");
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
				clickedValues.put(event.getComponent().getId(), "("+chart.getClickedImageMapArea().getXAxisLabel()
						+ "  |  " + chart.getClickedImageMapArea().getValue()+")");
			}else{
				 chart.getClickedImageMapArea().getValue();
			}
		}

	}

	public ChartData getRChartData() {
		if (trialHandler.getShowedObject() != null)
			return service.generateRecruitmentChart(trialHandler.getShowedObject());
		return null;
	}
	
	public ChartData getAChartData(){
		if (trialHandler.getShowedObject() != null)
			return service.generateArmChart(trialHandler.getShowedObject());
		return null;
	}
	
	public ChartData getRTrialSiteChartData(){
		if (trialHandler.getShowedObject() != null)
			return service.generateRecruitmentChartTrialSite(trialHandler.getShowedObject());
		return null;
	}
	
	public ChartData getRFactorsChartData() {
		if (trialHandler.getShowedObject() != null)
			return service.generateRecruitmentChartFactors(trialHandler.getShowedObject());
		return null;
	}

}
