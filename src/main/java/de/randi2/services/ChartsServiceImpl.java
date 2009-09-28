package de.randi2.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.List;

import de.randi2.model.TreatmentArm;
import de.randi2.model.Trial;
import de.randi2.model.TrialSite;
import de.randi2.model.TrialSubject;
import de.randi2.model.randomization.ChartData;

public class ChartsServiceImpl implements ChartsService {

	@Override
	public ChartData generateRecruitmentChart(Trial trial) {
		ChartData tempD = new ChartData();

		int plannedSubjects = 0;
		for (TreatmentArm arm : trial.getTreatmentArms()) {
			plannedSubjects += arm.getPlannedSubjects();
		}

		List<double[]> data = new ArrayList<double[]>();
		GregorianCalendar startDate = trial.getStartDate();
		GregorianCalendar endDate = trial.getEndDate();
		int monthStart = startDate.get(GregorianCalendar.MONTH);
		int monthEnd = endDate.get(GregorianCalendar.MONTH);
		ArrayList<String> xL = new ArrayList<String>();

		for (int year = startDate.get(GregorianCalendar.YEAR); year <= endDate
				.get(GregorianCalendar.YEAR); year++) {

			if (year != endDate.get(GregorianCalendar.YEAR)) {
				monthEnd = startDate.getMaximum(GregorianCalendar.MONTH);
			} else {
				monthEnd = endDate.get(GregorianCalendar.MONTH);
			}
			for (int month = monthStart; month <= monthEnd; month++) {
				double[] values = new double[2];
				values[0] = 0;
				if (data.size() > 0) {
					values[1] = data.get(data.size() - 1)[1];
				} else
					values[1] = 0;

				for (TrialSubject subject : trial.getSubjects()) {
					if (subject.getCreatedAt().get(GregorianCalendar.MONTH) == month
							&& subject.getCreatedAt().get(
									GregorianCalendar.YEAR) == year) {
						values[1] = values[1] + 1.0;
					}
				}
				xL.add(month + "." + year);
				data.add(values);
			}
			monthStart = startDate.getMinimum(GregorianCalendar.MONTH);
		}

		for (int i = 1; i <= data.size(); i++) {
			data.get(i - 1)[0] = i * (plannedSubjects / data.size());
		}

		tempD.setXLabels(xL);
		tempD.setData(data);
		return tempD;
	}

	@Override
	public ChartData generateArmChart(Trial trial) {
		ChartData chData = new ChartData();
		int i = 1;
		ArrayList<String> xL = new ArrayList<String>();
		ArrayList<double[]> data = new ArrayList<double[]>();
		for (TreatmentArm t : trial.getTreatmentArms()) {
			data.add(new double[] { t.getSubjects().size() });
			xL.add(Integer.toString(i));
			i++;
		}
		chData.setData(data);
		chData.setXLabels(xL);
		return chData;
	}

	@Override
	public ChartData generateRecruitmentChartTrialSite(Trial trial) {
		ChartData chData = new ChartData();
		ArrayList<String> xL = new ArrayList<String>();
		ArrayList<double[]> data = new ArrayList<double[]>();
		
		ArrayList<TrialSubject> subjects1 = new ArrayList<TrialSubject>(trial
				.getSubjects());
		double[] count = new double[trial.getParticipatingSites().size()];
		int i = 0;
		for (TrialSite site : trial.getParticipatingSites()) {
			ArrayList<TrialSubject> subjects = new ArrayList<TrialSubject>(subjects1);
			xL.add(site.getName());
			
			count[i] = 0;
			for (TrialSubject subject : subjects) {
				if (subject.getTrialSite().getName().equals(site.getName())) {
					count[i]++;
					subjects1.remove(subject);
				}
			}
			data.add(count);
			i++;
		}
		chData.setData(data);
		chData.setXLabels(xL);
		return chData;
	}

}
