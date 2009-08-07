package de.randi2.services;

import de.randi2.jsf.supportBeans.ChartData;
import de.randi2.model.Trial;

public interface ChartsService {
	
	public ChartData generateRecruitmentChart(Trial trial);

}
