package de.randi2.jsf.supportBeans;

import javax.faces.event.ActionEvent;

import com.icesoft.faces.component.outputchart.OutputChart;

import de.randi2.jsf.controllerBeans.TrialHandler;
import de.randi2.model.randomization.ChartData;
import de.randi2.services.ChartsService;

public class Charts {

	private TrialHandler trialHandler;

	public void setTrialHandler(TrialHandler trialHandler) {
		this.trialHandler = trialHandler;
	}

	private ChartsService service;

	public void setService(ChartsService service) {
		this.service = service;
	}

	/**
	 * Represents the value of the chart element which has been clicked by the
	 * user.
	 */
	private String clickedValue = "?";

	/**
	 * Action for the user interaction with the chart.
	 * 
	 * @param event
	 *            - JSF event
	 */
	public void action(ActionEvent event) {
		if (event.getSource() instanceof OutputChart) {
			OutputChart chart = (OutputChart) event.getSource();
			clickedValue = "";
			if (chart.getClickedImageMapArea().getXAxisLabel() != null) {
				setClickedValue(chart.getClickedImageMapArea().getXAxisLabel()
						+ "  :  " + chart.getClickedImageMapArea().getValue());
			}
		}

	}

	public String getClickedValue() {
		return clickedValue;
	}

	public void setClickedValue(String clickedValue) {
		this.clickedValue = clickedValue;
		System.out.println(this.clickedValue);
	}

	public ChartData getRChartData() {
		if (trialHandler.getShowedObject() != null)
			return service.generateRecruitmentChart(trialHandler.getShowedObject());
		return null;
	}

}
