package de.randi2.services;

import de.randi2.model.Trial;
import de.randi2.model.randomization.ChartData;

public interface ChartsService {
	
	public ChartData generateRecruitmentChart(Trial trial);
	
	public ChartData generateArmChart(Trial trial);
	
	public ChartData generateRecruitmentChartTrialSite(Trial trial);
	
	public ChartData generateRecruitmentChartFactors(Trial trial);

}
