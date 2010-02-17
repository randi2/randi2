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

import static de.randi2.utility.ArithmeticUtil.cartesianProduct;

import java.util.ArrayList;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import de.randi2.model.TreatmentArm;
import de.randi2.model.Trial;
import de.randi2.model.TrialSite;
import de.randi2.model.TrialSubject;
import de.randi2.model.criteria.AbstractCriterion;
import de.randi2.model.criteria.constraints.AbstractConstraint;
import de.randi2.model.randomization.ChartData;
import de.randi2.utility.StrataNameIDWrapper;

@Service("chartsService")
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
		ChartData chData = new ChartData();
		ArrayList<String> xL = new ArrayList<String>();
		ArrayList<double[]> data = new ArrayList<double[]>();
		HashMap<String, Double> strataCountMap = new HashMap<String, Double>();
		HashMap<String, String> strataNameMap = new HashMap<String, String>();

		HashMap<AbstractCriterion<?, ?>, List<AbstractConstraint<?>>> temp = new HashMap<AbstractCriterion<?, ?>, List<AbstractConstraint<?>>>();
		for (AbstractCriterion<?, ?> cr : trial.getCriteria()) {
			List<AbstractConstraint<?>> list = new ArrayList<AbstractConstraint<?>>();
			for (AbstractConstraint<?> co : cr.getStrata()) {
				list.add(co);
			}
			temp.put(cr, list);
		}
		Set<Set<StrataNameIDWrapper>> strataIds = new HashSet<Set<StrataNameIDWrapper>>();
		// minimum one constraint
		if (temp.size() >= 1) {
			for (AbstractCriterion<?, ?> cr : temp.keySet()) {
				Set<StrataNameIDWrapper> strataLevel = new HashSet<StrataNameIDWrapper>();
				for (AbstractConstraint<?> co : temp.get(cr)) {
					StrataNameIDWrapper wrapper = new StrataNameIDWrapper();
					wrapper.setStrataId(cr.getId() + "_" + co.getId());
					wrapper.setStrataName(cr.getName() + "_" + co.getUIName());
					strataLevel.add(wrapper);
				}
				strataIds.add(strataLevel);
			}
			//cartesianProduct only necessary for more then one criterions
			if(temp.size()>=2){
				strataIds = cartesianProduct(strataIds.toArray(new HashSet[0]));
			}else{
				Set<StrataNameIDWrapper> tempStrataIds =strataIds.iterator().next();
				Set<Set<StrataNameIDWrapper>> tempStrataIdsSet = new HashSet<Set<StrataNameIDWrapper>>();
				for(StrataNameIDWrapper wrapper : tempStrataIds){
					Set<StrataNameIDWrapper> next = new HashSet<StrataNameIDWrapper>();
					next.add(wrapper);
					tempStrataIdsSet.add(next);
				}
				strataIds = tempStrataIdsSet;
			}
			for (Set<StrataNameIDWrapper> set : strataIds) {
				List<StrataNameIDWrapper> stringStrat = new ArrayList<StrataNameIDWrapper>();
				for (StrataNameIDWrapper string : set) {
					stringStrat.add(string);
				}
				Collections.sort(stringStrat);

				String stratId = "";
				String stratName = "";
				for (StrataNameIDWrapper s : stringStrat) {
					stratId += s.getStrataId() + ";";
					stratName += s.getStrataName() + ";";
				}
				if (trial.isStratifyTrialSite()) {
					for (TrialSite site : trial.getParticipatingSites()) {
						String strataId = site.getId() + "__" + stratId;
						strataCountMap.put(strataId, new Double(0));
						strataNameMap.put(strataId, site.getName() + " | "
								+ stratName);
					}

				} else {
					strataCountMap.put(stratId, new Double(0));
					strataNameMap.put(stratId, stratName);
				}

			}
		}else if (trial.isStratifyTrialSite()) {
			for (TrialSite site : trial.getParticipatingSites()) {
				String strataId = site.getId() + "__";
				strataCountMap.put(strataId, new Double(0));
				strataNameMap.put(strataId, site.getName());
			}

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
