package de.randi2.core.unit.model;


import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.springframework.transaction.annotation.Transactional;

import de.randi2.model.TreatmentArm;
import de.randi2.model.Trial;
import de.randi2.model.TrialSubject;
import de.randi2.model.exceptions.ValidationException;
import de.randi2.testUtility.utility.AbstractDomainTest;

public class TreatmentArmTest extends AbstractDomainTest<TreatmentArm> {

	private TreatmentArm validTreatmentArm;
	
	public TreatmentArmTest(){
		super(TreatmentArm.class);
	}
	
	@Before
	public void setUp(){
		super.setUp();
		validTreatmentArm = new TreatmentArm();
		validTreatmentArm.setDescription("description");
		validTreatmentArm.setName("arm");
		validTreatmentArm.setPlannedSubjects(10);
		validTreatmentArm.setTrial(factory.getTrial());
	}
	
	@Test
	public void testSubjects(){
		assertTrue(validTreatmentArm.getSubjects().isEmpty());
		TrialSubject subject1 = new TrialSubject();
		subject1.setIdentification("id1");
		TrialSubject subject2 = new TrialSubject();
		subject2.setIdentification("id2");
		validTreatmentArm.addSubject(subject1);
		validTreatmentArm.addSubject(subject2);
		assertEquals(2, validTreatmentArm.getSubjects().size());
		assertTrue(validTreatmentArm.getSubjects().contains(subject1));
		assertTrue(validTreatmentArm.getSubjects().contains(subject2));
		
		List<TrialSubject> list = new ArrayList<TrialSubject>();
		list.add(new TrialSubject());
		
		validTreatmentArm.setSubjects(list);
		assertEquals(list, validTreatmentArm.getSubjects());
	}
	
	@Test
	public void testCheckValuePlannedSize(){
		try{
			validTreatmentArm.checkValue("plannedSubjects",0);
			fail();
		}catch (ValidationException e) {
			assertNotNull(e);
		}
		try{
			validTreatmentArm.checkValue("plannedSubjects",Long.MAX_VALUE);
			fail();
		}catch (ValidationException e) {
			assertNotNull(e);
		}
		try{
			validTreatmentArm.checkValue("plannedSubjects",30);
		}catch (ValidationException e) {
			fail();
		}
	}
	
	@Test
	public void testCheckValueTrial(){
		try{
			validTreatmentArm.checkValue("trial",validTreatmentArm.getTrial());
		}catch (ValidationException e) {
			fail();
		}
		try{
			validTreatmentArm.checkValue("trial",null);
			fail();
		}catch (ValidationException e) {
			assertNotNull(e);
		}
	}
	
	@Test
	public void testCheckValueName(){
		try{
			validTreatmentArm.checkValue("name","ABCDEF");
		}catch (ValidationException e) {
			fail();
		}
		try{
			validTreatmentArm.checkValue("name",stringUtil.getWithLength(1000));
			fail();
		}catch (ValidationException e) {
			assertNotNull(e);
		}
	}
	
	@Test
	@Transactional
	public void databaseIntegrationTest(){
		sessionFactory.getCurrentSession().persist(validTreatmentArm.getTrial().getLeadingSite());
		sessionFactory.getCurrentSession().persist(validTreatmentArm.getTrial().getSponsorInvestigator());
		sessionFactory.getCurrentSession().persist(validTreatmentArm.getTrial());
		sessionFactory.getCurrentSession().persist(validTreatmentArm);
		assertTrue(validTreatmentArm.getId() >0);
		TreatmentArm dbArm = (TreatmentArm)sessionFactory.getCurrentSession().get(TreatmentArm.class, validTreatmentArm.getId());
		assertEquals(validTreatmentArm.getDescription(), dbArm.getDescription());
		assertEquals(validTreatmentArm.getName(), dbArm.getName());
		assertEquals(validTreatmentArm.getPlannedSubjects(),dbArm.getPlannedSubjects());
		assertEquals(validTreatmentArm.getTrial().getId(), dbArm.getTrial().getId());
		assertEquals(validTreatmentArm.getTrial().getName(), dbArm.getTrial().getName());
	}
	
	@Test
	public void testEqualsHashCode(){
		TreatmentArm arm1 = new TreatmentArm();
		TreatmentArm arm2 = new TreatmentArm();
		assertTrue(arm1.equals(arm2));
		assertEquals(arm1.hashCode(), arm2.hashCode());
		
		arm1.setId(12);
		arm2.setId(12);
		assertTrue(arm1.equals(arm2));
		assertEquals(arm1.hashCode(), arm2.hashCode());
		
		arm1.setName("name");
		arm2.setName("name");
		assertTrue(arm1.equals(arm2));
		assertEquals(arm1.hashCode(), arm2.hashCode());
		
		arm1.setDescription("name");
		arm2.setDescription("name");
		assertTrue(arm1.equals(arm2));
		assertEquals(arm1.hashCode(), arm2.hashCode());
		
		arm1.setPlannedSubjects(10);
		arm2.setPlannedSubjects(10);
		assertTrue(arm1.equals(arm2));
		assertEquals(arm1.hashCode(), arm2.hashCode());
		
		arm1.setId(12);
		arm2.setId(13);
		assertFalse(arm1.equals(arm2));
		arm2.setId(12);
		assertTrue(arm1.equals(arm2));
		
		arm1.setName("name");
		arm2.setName("name1");
		assertFalse(arm1.equals(arm2));
		arm2.setName("name");
		assertTrue(arm1.equals(arm2));
		assertEquals(arm1.hashCode(), arm2.hashCode());
		
		arm1.setDescription("name");
		arm2.setDescription("name1");
		assertFalse(arm1.equals(arm2));
		arm2.setDescription("name");
		assertTrue(arm1.equals(arm2));
		assertEquals(arm1.hashCode(), arm2.hashCode());
		
		arm1.setPlannedSubjects(10);
		arm2.setPlannedSubjects(11);
		assertFalse(arm1.equals(arm2));
		arm2.setPlannedSubjects(10);
		assertTrue(arm1.equals(arm2));
		assertEquals(arm1.hashCode(), arm2.hashCode());
		
		arm1.setVersion(256);
		assertTrue(arm1.equals(arm2));
		assertEquals(arm1.hashCode(), arm2.hashCode());
		
		assertFalse(arm1.equals(null));
		assertFalse(arm1.equals(new Trial()));
	}
	
	
	@Test
	public void testGetRequieredFields(){
		Map<String, Boolean> map = (new TreatmentArm()).getRequiredFields();
		for(String key : map.keySet()){
			if(key.equals("name")) {assertTrue(map.get(key));} 
			else if(key.equals("description")) {assertFalse(map.get(key));} 
			else if(key.equals("plannedSubjects")) {assertFalse(map.get(key));}  
			else if(key.equals("trial")) {assertTrue(map.get(key));} 
			else if(key.equals("subjects")) {assertFalse(map.get(key));} 
			else if(key.equals("serialVersionUID")) {assertFalse(map.get(key));}
			else if(key.equals("$VRc")) {assertFalse(map.get(key));}
			else fail(key + " not checked");
		}
	}
	
	@Test
	public void testToString(){
		assertNotNull(validTreatmentArm.toString());
	}
	
	@Test
	public void testUiName(){
		validTreatmentArm.setName("valid name");
		assertEquals("valid name", validTreatmentArm.getUIName());
	}
	
	@Test
	public void testPlannedSubjects(){
		validTreatmentArm.setPlannedSubjects(123456);
		assertEquals(123456, validTreatmentArm.getPlannedSubjects());
	}
	
	@Test
	public void testCurrentSubjectAmountAndFillLevel(){
		validTreatmentArm.setPlannedSubjects(100);
		for(int i=1;i<=100;i++){
			validTreatmentArm.addSubject(new TrialSubject());
			assertEquals(i, validTreatmentArm.getCurrentSubjectsAmount());
			DecimalFormat f = new DecimalFormat("#0.00");
			assertEquals(f.format(i), f.format(validTreatmentArm.getFillLevel()));
		}
	}
}
