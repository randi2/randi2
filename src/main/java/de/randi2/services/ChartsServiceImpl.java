package de.randi2.services;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import de.randi2.model.TreatmentArm;
import de.randi2.model.Trial;
import de.randi2.model.TrialSite;
import de.randi2.model.TrialSubject;
import de.randi2.model.criteria.AbstractCriterion;
import de.randi2.model.criteria.constraints.AbstractConstraint;
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
			i++;
		}
		for(double j : count){
			data.add(new double[]{j});
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
	

		HashMap<AbstractCriterion<?,?>, List<Long>> temp= new HashMap<AbstractCriterion<?,?>, List<Long>>();
		for (AbstractCriterion<?,?> cr : trial.getCriteria()) {
			List<Long> list = new ArrayList<Long>();
				for(AbstractConstraint<?> co : cr.getStrata()){
					list.add(co.getId());
				}
			temp.put(cr, list);
		}
		
		Set<Set<Object>> strataIds = new HashSet<Set<Object>>();
		
		for(AbstractCriterion<?,?> cr : temp.keySet()){
			Set<Object> strataLevel = new HashSet<Object>();
			for(Long id : temp.get(cr)){
				strataLevel.add(cr.getId()+"_"+id);
			}
			strataIds.add(strataLevel);
		}
		strataIds = cartesianProduct(strataIds);
		
		
		
		for (TrialSubject subject : trial.getSubjects()) {
				Double count = strataCountMap.get(subject.getStratum());
				if(count==null){
					count = 0.0;
				}else{
					count++;
				}
				strataCountMap.put(subject.getStratum(), count);
			}
	
		for(String s :strataCountMap.keySet()){
			xL.add(s);
			data.add(new double[]{strataCountMap.get(s)});
		}
		chData.setData(data);
		chData.setXLabels(xL);
		return chData;
	}
	
	/**
	 * from http://stackoverflow.com/questions/714108/cartesian-product-of-arbitrary-sets-in-java
	 * @param sets
	 * @return
	 */
	public static Set<Set<Object>> cartesianProduct(Set<?>... sets) {
	    if (sets.length < 2)
	        throw new IllegalArgumentException(
	                "Can't have a product of fewer than two sets (got " +
	                sets.length + ")");

	    return _cartesianProduct(0, sets);
	}

	private static Set<Set<Object>> _cartesianProduct(int index, Set<?>... sets) {
	    Set<Set<Object>> ret = new HashSet<Set<Object>>();
	    if (index == sets.length) {
	        ret.add(new HashSet<Object>());
	    } else {
	        for (Object obj : sets[index]) {
	            for (Set<Object> set : _cartesianProduct(index+1, sets)) {
	                set.add(obj);
	                ret.add(set);
	            }
	        }
	    }
	    return ret;
	}

	
}
