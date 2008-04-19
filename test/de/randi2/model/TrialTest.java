package de.randi2.model;

import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.test.annotation.NotTransactional;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/de/randi2/applicationContext.xml" })
@Transactional
public class TrialTest {

	@Autowired
	protected HibernateTemplate hibernameTemplate;

	private Trial emptyTrial;
	private Trial validTrial;

	// Testdaten
	private String nameOK1 = "Contargan vs. Placebo";
	private String nameOK2 = "MA";
	private String nameToShort = "K";

	@Before
	public void setUp() throws Exception {
		this.emptyTrial = new Trial();

		// Valides Trial
		validTrial = new Trial();
		validTrial.setName("Aspirin vs. Placebo");

	}

	@Test
	@NotTransactional
	public void testNameBasic() {
		validTrial.setName(nameOK1);
		assertEquals(nameOK1, validTrial.getName());

		// Richtiger Name
		validTrial.setName(nameOK2);
		assertEquals(nameOK2, validTrial.getName());
	}
	
	@Test
	public void testNameLength() {

		validTrial.setName(nameToShort);
		hibernameTemplate.saveOrUpdate(validTrial);

	}

}
