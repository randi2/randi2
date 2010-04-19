package de.randi2.model;

import static junit.framework.Assert.*;
import static org.junit.Assert.assertFalse;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

import de.randi2.model.criteria.DichotomousCriterion;
import de.randi2.model.criteria.OrdinalCriterion;
import de.randi2.model.criteria.constraints.DichotomousConstraint;
import de.randi2.model.criteria.constraints.OrdinalConstraint;
import de.randi2.unsorted.ContraintViolatedException;

public class TrialSubjectTest extends AbstractDomainObject {
	private static final long serialVersionUID = 4476774735316414165L;
	private long id = 0;

	private long getNextId() {
		return id++;
	}

	private DichotomousCriterion dCriterion1 = null;

	private SubjectProperty<String> getEmptyDichotomProperty1() {
		if (dCriterion1 == null) {
			dCriterion1 = new DichotomousCriterion();

			dCriterion1.setId(getNextId());
			dCriterion1.setOption1("option1");
			dCriterion1.setOption2("option2");
			try {
				List<String> value = new ArrayList<String>();
				value.add(dCriterion1.getOption1());
				DichotomousConstraint co = new DichotomousConstraint(value);
				co.setId(getNextId());
				dCriterion1.addStrata(co);
				value.clear();
				value.add(dCriterion1.getOption2());
				co = new DichotomousConstraint(value);
				co.setId(getNextId());
				dCriterion1.addStrata(co);
			} catch (ContraintViolatedException e) {
				fail(e.getMessage());
			}
		}
		return new SubjectProperty<String>(dCriterion1);

	}

	private DichotomousCriterion dCriterion2 = null;

	private SubjectProperty<String> getEmptyDichotomProperty2() {
		if (dCriterion2 == null) {
			dCriterion2 = new DichotomousCriterion();

			dCriterion2.setId(getNextId());
			dCriterion2.setOption1("option1");
			dCriterion2.setOption2("option2");
			try {
				List<String> value = new ArrayList<String>();
				value.add(dCriterion2.getOption1());
				DichotomousConstraint co = new DichotomousConstraint(value);
				co.setId(getNextId());
				dCriterion2.addStrata(co);
				value.clear();
				value.add(dCriterion2.getOption2());
				co = new DichotomousConstraint(value);
				co.setId(getNextId());
				dCriterion2.addStrata(co);
			} catch (ContraintViolatedException e) {
				fail(e.getMessage());
			}
		}
		return new SubjectProperty<String>(dCriterion2);

	}

	private DichotomousCriterion dCriterion3 = null;

	private SubjectProperty<String> getEmptyDichotomProperty3() {
		if (dCriterion3 == null) {
			dCriterion3 = new DichotomousCriterion();

			dCriterion3.setId(getNextId());
			dCriterion3.setOption1("option1");
			dCriterion3.setOption2("option2");
			try {
				List<String> value = new ArrayList<String>();
				value.add(dCriterion3.getOption1());
				DichotomousConstraint co = new DichotomousConstraint(value);
				co.setId(getNextId());
				dCriterion3.addStrata(co);
				value.clear();
				value.add(dCriterion3.getOption2());
				co = new DichotomousConstraint(value);
				co.setId(getNextId());
				dCriterion3.addStrata(co);
			} catch (ContraintViolatedException e) {
				fail(e.getMessage());
			}
		}
		return new SubjectProperty<String>(dCriterion3);

	}

	private OrdinalCriterion oCriterion1 = null;

	private SubjectProperty<String> getEmptyOrdinalProperty1() {
		if (oCriterion1 == null) {
			oCriterion1 = new OrdinalCriterion();

			oCriterion1.setId(getNextId());
			List<String> elements = new ArrayList<String>();
			elements.add("option1");
			elements.add("option2");
			elements.add("option3");
			elements.add("option4");
			elements.add("option5");
			elements.add("option6");
			oCriterion1.setElements(elements);
			try {
				List<String> value = new ArrayList<String>();
				value.add("option1");
				OrdinalConstraint co = new OrdinalConstraint(value);
				co.setId(getNextId());
				oCriterion1.addStrata(co);
				value.clear();
				value.add("option2");
				co = new OrdinalConstraint(value);
				co.setId(getNextId());
				oCriterion1.addStrata(co);
				value.clear();
				value.add("option3");
				co = new OrdinalConstraint(value);
				co.setId(getNextId());
				oCriterion1.addStrata(co);
				value.clear();
				value.add("option4");
				co = new OrdinalConstraint(value);
				co.setId(getNextId());
				oCriterion1.addStrata(co);
				value.clear();
				value.add("option5");
				co = new OrdinalConstraint(value);
				co.setId(getNextId());
				oCriterion1.addStrata(co);
				value.clear();
				value.add("option6");
				co = new OrdinalConstraint(value);
				co.setId(getNextId());
				oCriterion1.addStrata(co);
			} catch (ContraintViolatedException e) {
				fail(e.getMessage());
			}
		}
		return new SubjectProperty<String>(oCriterion1);

	}

	@Test
	public void testGetStratum1() {
		// every entry grouped the members of one group
		List<List<TrialSubject>> subjects = new ArrayList<List<TrialSubject>>();
		for (int j = 1; j <= 2; j++) {
			for (int k = 1; k <= 2; k++) {
				for (int l = 1; l <= 2; l++) {
					List<TrialSubject> subjectsGroup = new ArrayList<TrialSubject>();
					// generate objects with same group
					for (int i = 0; i < 3; i++) {
						TrialSubject subject = new TrialSubject();
						Set<SubjectProperty<?>> properties = new HashSet<SubjectProperty<?>>();
						SubjectProperty<String> p1 = getEmptyDichotomProperty1();
						properties.add(p1);
						SubjectProperty<String> p2 = getEmptyDichotomProperty2();
						properties.add(p2);
						SubjectProperty<String> p3 = getEmptyDichotomProperty3();
						properties.add(p3);
						try {
							p1.setValue("option" + j);
							p2.setValue("option" + k);
							p3.setValue("option" + l);
						} catch (ContraintViolatedException e) {
							fail(e.getMessage());
						}
						subject.setProperties(properties);
						subjectsGroup.add(subject);
					}
					subjects.add(subjectsGroup);
				}

			}
		}
		assertEquals(8, subjects.size());
		testStratification(subjects);
	}

	@Test
	public void testGetStratum2() {
		// every entry grouped the members of one group
		List<List<TrialSubject>> subjects = new ArrayList<List<TrialSubject>>();
		for (int j = 1; j <= 2; j++) {
			for (int k = 1; k <= 2; k++) {
				for (int l = 1; l <= 2; l++) {
					for (int m = 1; m <= 6; m++) {
						List<TrialSubject> subjectsGroup = new ArrayList<TrialSubject>();
						// generate objects with same group
						for (int i = 0; i < 3; i++) {
							TrialSubject subject = new TrialSubject();
							Set<SubjectProperty<?>> properties = new HashSet<SubjectProperty<?>>();
							SubjectProperty<String> p1 = getEmptyDichotomProperty1();
							properties.add(p1);
							SubjectProperty<String> p2 = getEmptyDichotomProperty2();
							properties.add(p2);
							SubjectProperty<String> p3 = getEmptyDichotomProperty3();
							properties.add(p3);
							SubjectProperty<String> p4 = getEmptyOrdinalProperty1();
							properties.add(p4);
							try {
								p1.setValue("option" + j);
								p2.setValue("option" + k);
								p3.setValue("option" + l);
								p4.setValue("option" + m);
							} catch (ContraintViolatedException e) {
								fail(e.getMessage());
							}
							subject.setProperties(properties);
							subjectsGroup.add(subject);
						}
						subjects.add(subjectsGroup);
					}
				}

			}
		}
		assertEquals(48, subjects.size());
		testStratification(subjects);
	}

	private void testStratification(List<List<TrialSubject>> subjects) {
		for (int i = 0; i < subjects.size(); i++) {
			List<TrialSubject> subs = subjects.get(i);
			// Test getStratum in one group
			for (int k = 0; k < subs.size() - 1; k++) {
				for (int l = k + 1; l < subs.size(); l++) {
					assertEquals(subs.get(k).getStratum(), subs.get(l)
							.getStratum());
				}
			}
			for (int j = i + 1; j < subs.size(); j++) {
				assertFalse(subjects.get(i).get(0).getStratum().equals(
						subjects.get(j).get(0).getStratum()));
			}
			
		}
	}
	
	@Test
	public void testGetRequieredFields(){
		Map<String, Boolean> map = (new TrialSubject()).getRequiredFields();
		for(String key : map.keySet()){
			if(key.equals("identification")) {assertTrue(map.get(key));} 
			else if(key.equals("randNumber")) {assertTrue(map.get(key));} 
			else if(key.equals("counter")) {assertFalse(map.get(key));}  
			else if(key.equals("trialSite")) {assertFalse(map.get(key));} 
			else if(key.equals("arm")) {assertTrue(map.get(key));} 
			else if(key.equals("properties")) {assertFalse(map.get(key)); }
			else if(key.equals("investigator")) {assertFalse(map.get(key));} 
			else if(key.equals("serialVersionUID")) {assertFalse(map.get(key));}
			else if(key.equals("$VRc")) {assertFalse(map.get(key));}
			else fail(key + " not checked");
		}
	}
}
