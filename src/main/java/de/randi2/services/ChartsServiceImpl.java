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
package de.randi2.services;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.randi2.dao.TrialDao;
import de.randi2.model.TreatmentArm;
import de.randi2.model.Trial;
import de.randi2.model.TrialSite;
import de.randi2.model.TrialSubject;
import de.randi2.utility.Pair;

@Service("chartsService")
public class ChartsServiceImpl implements ChartsService {

	@Autowired
	private TrialDao trialDao;
	
	@Override
	public ChartData generateRecruitmentChart(Trial trial) {
		trial = trialDao.refresh(trial);
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
				xL.add((month + 1) + "." + year);
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
		trial = trialDao.refresh(trial);
		ChartData chData = new ChartData();
		ArrayList<String> xL = new ArrayList<String>();
		ArrayList<double[]> data = new ArrayList<double[]>();

		ArrayList<TrialSubject> subjects1 = new ArrayList<TrialSubject>(trial
				.getSubjects());
		double[] count = new double[trial.getParticipatingSites().size()];
		int i = 0;
		for (TrialSite site : trial.getParticipatingSites()) {
			ArrayList<TrialSubject> subjects = new ArrayList<TrialSubject>(
					subjects1);
			xL.add(site.getName());

			count[i] = 0;
			for (TrialSubject subject : subjects) {
				if (subject.getTrialSite().getName().equals(site.getName())) {
					count[i]++;
					subjects1.remove(subject);
				}
			}
			i++;
		}
		for (double j : count) {
			data.add(new double[] { j });
		}
		chData.setData(data);
		chData.setXLabels(xL);
		return chData;
	}

	@Override
	public ChartData generateRecruitmentChartFactors(Trial trial) {
		trial = trialDao.refresh(trial);
		ChartData chData = new ChartData();
		ArrayList<String> xL = new ArrayList<String>();
		ArrayList<double[]> data = new ArrayList<double[]>();
		HashMap<String, Double> strataCountMap = new HashMap<String, Double>();
		HashMap<String, String> strataNameMap = new HashMap<String, String>();

		Pair<List<String>,List<String>> pair = trial.getAllStrataIdsAndNames();
		for(int i = 0;i<pair.first().size();i++){
			strataCountMap.put(pair.first().get(i), new Double(0));
			strataNameMap.put(pair.first().get(i), pair.last().get(i));
		}
			
		for (TrialSubject subject : trial.getSubjects()) {
			String stratum = "";
			if (trial.isStratifyTrialSite()) {
				stratum = subject.getTrialSite().getId() + "__";
			}
			stratum += subject.getStratum();
			Double count = strataCountMap.get(stratum);
			count++;
			strataCountMap.put(stratum, count);
		}

		double[] dataTable;
		int i = 0;
		for (String s : strataCountMap.keySet()) {
			dataTable = new double[strataCountMap.size()];
			for (int j = 0; j < dataTable.length; j++) {
				if (j != i) {
					dataTable[j] = 0;
				}
			}
			dataTable[i] = strataCountMap.get(s);
			xL.add(strataNameMap.get(s));
			i++;
			data.add(dataTable);
		}
		chData.setData(data);
		chData.setXLabels(xL);
		return chData;
	}

}
