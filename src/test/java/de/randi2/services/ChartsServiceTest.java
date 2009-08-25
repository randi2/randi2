package de.randi2.services;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import de.randi2.model.TreatmentArm;
import de.randi2.model.Trial;
import de.randi2.model.TrialSubject;
import de.randi2.model.randomization.BlockRandomizationConfig;
import de.randi2.model.randomization.ChartData;

public class ChartsServiceTest extends AbstractServiceTest{

	@Autowired private TrialService trialService;
	@Autowired private ChartsService chartsService;
	
	private Trial validTrial;
	
	
	@Override
	public void setUp() {
		
		super.setUp();
		validTrial = factory.getTrial();
		validTrial.setLeadingSite(admin.getPerson().getTrialSite());
		validTrial.setSponsorInvestigator(admin.getPerson());
		
	
	}
	
	
	
	private void randomizeInValidTrialOneYear(){
		validTrial.setStartDate(new GregorianCalendar(2009, 0, 1));
		validTrial.setEndDate(new GregorianCalendar(2009, 11, 1));
		int blocksize = 4;
		int randomizations = 120;
		TreatmentArm arm1 = new TreatmentArm();
		arm1.setPlannedSubjects(randomizations/2);
		arm1.setName("arm1");
		arm1.setTrial(validTrial);
		TreatmentArm arm2 = new TreatmentArm();
		arm2.setPlannedSubjects(randomizations/2);
		arm2.setName("arm2");
		arm2.setTrial(validTrial);
		List<TreatmentArm> arms = new ArrayList<TreatmentArm>();
		arms.add(arm1);
		arms.add(arm2);
	
		trialService.create(validTrial);
		validTrial.setTreatmentArms(arms);
		BlockRandomizationConfig config =  new BlockRandomizationConfig();
		config.setMaximum(blocksize);
		config.setMinimum(blocksize);
		validTrial.setRandomizationConfiguration(config);
		trialService.update(validTrial);
		assertTrue(validTrial.getId()>0);
		assertEquals(2,validTrial.getTreatmentArms().size());
	
		for(int i=0;i<randomizations;i++){
			TrialSubject subject = new TrialSubject();
			 subject.setIdentification("identification" + i);
			 trialService.randomize(validTrial,subject );
			 subject.setCreatedAt(new GregorianCalendar(2009, i%12, 1));
			 sessionFactory.getCurrentSession().update(subject);
			if((i%blocksize)==(blocksize-1)){
			assertEquals(validTrial.getTreatmentArms().get(0).getSubjects().size() ,validTrial.getTreatmentArms().get(1).getSubjects().size());
			}
			
			int diff=validTrial.getTreatmentArms().get(0).getSubjects().size() -validTrial.getTreatmentArms().get(1).getSubjects().size();
			assertTrue((blocksize/2)>=diff && (-1)*(blocksize/2)<=diff);
		}
		
		Trial dbTrial = trialService.getObject(validTrial.getId());
		assertNotNull(dbTrial);
		assertEquals(validTrial.getName(), dbTrial.getName());
		assertEquals(2, dbTrial.getTreatmentArms().size());
		assertEquals(randomizations, dbTrial.getTreatmentArms().get(0).getSubjects().size() + dbTrial.getTreatmentArms().get(1).getSubjects().size());
		assertEquals(randomizations/2, dbTrial.getTreatmentArms().get(0).getSubjects().size());
		assertEquals(randomizations/2, dbTrial.getTreatmentArms().get(1).getSubjects().size());
	}
	
	private void randomizeInValidTrialTwoYears(){
		validTrial.setStartDate(new GregorianCalendar(2009, 0, 1));
		validTrial.setEndDate(new GregorianCalendar(2010, 11, 1));
		int blocksize = 4;
		int randomizations = 240;
		TreatmentArm arm1 = new TreatmentArm();
		arm1.setPlannedSubjects(randomizations/2);
		arm1.setName("arm1");
		arm1.setTrial(validTrial);
		TreatmentArm arm2 = new TreatmentArm();
		arm2.setPlannedSubjects(randomizations/2);
		arm2.setName("arm2");
		arm2.setTrial(validTrial);
		List<TreatmentArm> arms = new ArrayList<TreatmentArm>();
		arms.add(arm1);
		arms.add(arm2);
	
		trialService.create(validTrial);
		validTrial.setTreatmentArms(arms);
		BlockRandomizationConfig config =  new BlockRandomizationConfig();
		config.setMaximum(blocksize);
		config.setMinimum(blocksize);
		validTrial.setRandomizationConfiguration(config);
		trialService.update(validTrial);
		assertTrue(validTrial.getId()>0);
		assertEquals(2,validTrial.getTreatmentArms().size());
	
		for(int i=0;i<randomizations;i++){
			TrialSubject subject = new TrialSubject();
			 subject.setIdentification("identification" + i);
			 trialService.randomize(validTrial,subject );
			 subject.setCreatedAt(new GregorianCalendar(2009 + (i>=120? 1:0), i%12, 1));
			 sessionFactory.getCurrentSession().update(subject);
			if((i%blocksize)==(blocksize-1)){
			assertEquals(validTrial.getTreatmentArms().get(0).getSubjects().size() ,validTrial.getTreatmentArms().get(1).getSubjects().size());
			}
			
			int diff=validTrial.getTreatmentArms().get(0).getSubjects().size() -validTrial.getTreatmentArms().get(1).getSubjects().size();
			assertTrue((blocksize/2)>=diff && (-1)*(blocksize/2)<=diff);
		}
		
		Trial dbTrial = trialService.getObject(validTrial.getId());
		assertNotNull(dbTrial);
		assertEquals(validTrial.getName(), dbTrial.getName());
		assertEquals(2, dbTrial.getTreatmentArms().size());
		assertEquals(randomizations, dbTrial.getTreatmentArms().get(0).getSubjects().size() + dbTrial.getTreatmentArms().get(1).getSubjects().size());
		assertEquals(randomizations/2, dbTrial.getTreatmentArms().get(0).getSubjects().size());
		assertEquals(randomizations/2, dbTrial.getTreatmentArms().get(1).getSubjects().size());
	}
	
	
	
	
	@Test
	public void testGenerateRecruitmentChart1(){
		randomizeInValidTrialOneYear();
		ChartData chartData = chartsService.generateRecruitmentChart(validTrial);
		assertEquals(12, chartData.getXLabels().size());
		assertEquals(12, chartData.getData().size());
		for(int i = 0;i< chartData.getData().size();i++){
			assertEquals(10.0*(i+1), chartData.getData().get(i)[0]);
			assertEquals(10.0*(i+1), chartData.getData().get(i)[1]);
		}
		
	}
	
	@Test
	public void testGenerateRecruitmentChart2(){
		randomizeInValidTrialTwoYears();
		ChartData chartData = chartsService.generateRecruitmentChart(validTrial);
		assertEquals(24, chartData.getXLabels().size());
		assertEquals(24, chartData.getData().size());
		for(int i = 0;i< chartData.getData().size();i++){
			assertEquals(10.0*(i+1), chartData.getData().get(i)[0]);
			assertEquals(10.0*(i+1), chartData.getData().get(i)[1]);
		}
		
	}
}
