package de.randi2.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.hibernate.validator.ClassValidator;
import org.hibernate.validator.InvalidStateException;
import org.hibernate.validator.InvalidValue;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import de.randi2.utility.TestStringUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/de/randi2/applicationContext.xml" })
public class TrialTest extends DomainObjectTest<Trial>{

	public TrialTest() {
		super(Trial.class);
	}

	@Autowired
	private HibernateTemplate hibernameTemplate;
	@Autowired
	private TestStringUtil testStringUtil;

	private Trial validTrial;

	@Before
	public void setUp() throws Exception {
		// Valides Trial
		validTrial = new Trial();
		validTrial.setName("Aspirin vs. Placebo");

	}

	@Test
	public void testConstructor() {
		final Trial t = new Trial();
		assertNull(t.getName());
		assertNull(t.getDescription());
		assertNull(t.getStartDate());
		assertNull(t.getEndDate());
		assertEquals(Trial.TrialStatus.IN_PREPARATION, t.getStatus());
		assertNull(t.getProtocol());
	}

	@Test
	public void testName() {
		final String nameOK1 = "C";
		final String nameOK2 = testStringUtil.getWithLength(100);
		
		final String nameJustOK = testStringUtil.getWithLength(255);
		final String nameToLong = testStringUtil.getWithLength(256);
		final String nameEmpty = "";
		final String nameNull = null;

		validTrial.setName(nameOK1);
		assertEquals(nameOK1, validTrial.getName());

		// Richtiger Name
		validTrial.setName(nameOK2);
		assertEquals(nameOK2, validTrial.getName());
		assertValid(validTrial);

		validTrial.setName(nameToLong);
		assertEquals(nameToLong, validTrial.getName());
		assertInvalid(validTrial, new String[]{Trial.NAME_TO_LONG});
		
		validTrial.setName(nameJustOK);
		assertEquals(nameJustOK, validTrial.getName());
		assertValid(validTrial);
		
		validTrial.setName(nameEmpty);
		assertEquals(nameEmpty, validTrial.getName());
		assertInvalid(validTrial, new String[]{Trial.NAME_EMPTY});
		
		validTrial.setName(nameNull);
		assertEquals(nameNull, validTrial.getName());
		assertInvalid(validTrial, new String[]{Trial.NAME_EMPTY});
	}
	
	@Test
	public void testDescription() {
		final String emptyDescribtion = "";
		final String nullDescribtion = null;
		final String longDescribtion = "Ich bin Blindtext. Von Geburt an. Es hat lange gedauert, bis ich begriffen habe, was es bedeutet, ein blinder Text zu sein: Man macht keinen Sinn. Man wirkt hier und da aus dem Zusammenhang gerissen. Oft wird man gar nicht erst gelesen. Aber bin ich deshalb ein schlechter Text? Ich weiss, dass ich nie die Chance haben werde, im Stern zu erscheinen. Aber bin ich darum weniger wichtig? Ich bin blind! Aber ich bin gerne Text. Und sollten Sie mich jetzt tatsächlich zu Ende lesen, dann habe ich etwas geschafft, was den meisten normalen Texten nicht gelingt. Ich bin Blindtext. Von Geburt an. Es hat lange gedauert, bis ich begriffen habe, was es bedeutet, ein blinder Text zu sein: Man macht keinen Sinn. Man wirkt hier und da aus dem Zusammenhang gerissen. Oft wird man gar nicht erst gelesen. Aber bin ich deshalb ein schlechter Text? Ich weiss, dass ich nie die Chance haben werde, im Stern zu erscheinen. Aber bin ich darum weniger wichtig? Ich bin blind! Aber ich bin gerne Text. Und sollten Sie mich jetzt tatsächlich zu Ende lesen, dann habe ich etwas geschafft, was den meisten normalen Texten nicht gelingt. Ich bin Blindtext. Von Geburt an. Es hat lange gedauert, bis ich begriffen habe, was es bedeutet, ein blinder Text zu sein: Man macht keinen Sinn. Man wirkt hier und da aus dem Zusammenhang gerissen. Oft wird man gar nicht erst gelesen. Aber bin ich deshalb ein schlechter Text? Ich weiss, dass ich nie die Chance haben werde, im Stern zu erscheinen. Aber bin ich darum weniger wichtig? Ich bin blind! Aber ich bin gerne Text. Und sollten Sie mich jetzt tatsächlich zu Ende lesen, dann habe ich etwas geschafft, was den meisten normalen Texten nicht gelingt. Ich bin Blindtext. Von Geburt an. Es hat lange gedauert, bis ich begriffen habe, was es bedeutet, ein blinder Text zu sein: Man macht keinen Sinn. Man wirkt hier und da aus dem Zusammenhang gerissen. Oft wird man gar nicht erst gelesen. Aber bin ich deshalb ein schlechter Text? Ich weiss, dass ich nie die Chance haben werde, im Stern zu erscheinen. Aber bin ich darum weniger wichtig? Ich bin blind! Aber ich bin gerne Text. Und sollten Sie mich jetzt tatsächlich zu Ende lesen, dann habe ich etwas geschafft, was den meisten normalen Texten nicht gelingt. Ich bin Blindtext. Von Geburt an. Es hat lange gedauert, bis ich begriffen habe, was es bedeutet, ein blinder Text zu sein: Man macht keinen Sinn. Man wirkt hier und da aus dem Zusammenhang gerissen. Oft wird man gar nicht erst gelesen. Aber bin ich deshalb ein schlechter Text? Ich weiss, dass ich nie die Chance haben werde, im Stern zu erscheinen. Aber bin ich darum weniger wichtig? Ich bin blind! Aber ich bin gerne Text. Und sollten Sie mich jetzt tatsächlich zu Ende lesen, dann habe ich etwas geschafft, was den meisten normalen Texten nicht gelingt. Ich bin Blindtext. Von Geburt an. Es hat lange gedauert, bis ich begriffen habe, was es bedeutet, ein blinder Text zu sein: Man macht keinen Sinn. Man wirkt hier und da aus dem Zusammenhang gerissen. Oft wird man gar nicht erst gelesen. Aber bin ich deshalb ein schlechter Text? Ich weiss, dass ich nie die Chance haben werde, im Stern zu erscheinen. Aber bin ich darum weniger wichtig? Ich bin blind! Aber ich bin gerne Text. Und sollten Sie mich jetzt tatsächlich zu Ende lesen, dann habe ich etwas geschafft, was den meisten normalen Texten nicht gelingt. Ich bin Blindtext. Von Geburt an. Es hat lange gedauert, bis ich begriffen habe, was es bedeutet, ein blinder Text zu sein: Man macht keinen Sinn. Man wirkt hier und da aus dem Zusammenhang gerissen. Oft wird man gar nicht erst gelesen. Aber bin ich deshalb ein schlechter Text? Ich weiss, dass ich nie die Chance haben werde, im Stern zu erscheinen. Aber bin ich darum weniger wichtig? Ich bin blind! Aber ich bin gerne Text. Und sollten Sie mich jetzt tatsächlich zu Ende lesen, dann habe ich etwas geschafft, was den meisten normalen Texten nicht gelingt. Ich bin Blindtext. Von Geburt an. Es hat lange gedauert, bis ich begriffen habe, was es bedeutet, ein blinder Text zu sein: Man macht keinen Sinn. Man wirkt hier und da aus dem Zusammenhang gerissen. Oft wird man gar nicht erst gelesen. Aber bin ich deshalb ein schlechter Text? Ich weiss, dass ich nie die Chance haben werde, im Stern zu erscheinen. Aber bin ich darum weniger wichtig? Ich bin blind! Aber ich bin gerne Text. Und sollten Sie mich jetzt tatsächlich zu Ende lesen, dann habe ich etwas geschafft, was den meisten normalen Texten nicht gelingt. Ich bin Blindtext. Von Geburt an. Es hat lange gedauert, bis ich begriffen habe, was es bedeutet, ein blinder Text zu sein: Man macht keinen Sinn. Man wirkt hier und da aus dem Zusammenhang gerissen. Oft wird man gar nicht erst gelesen. Aber bin ich deshalb ein schlechter Text? Ich weiss, dass ich nie die Chance haben werde, im Stern zu erscheinen. Aber bin ich darum weniger wichtig? Ich bin blind! Aber ich bin gerne Text. Und sollten Sie mich jetzt tatsächlich zu Ende lesen, dann habe ich etwas geschafft, was den meisten ";

		validTrial.setDescription(emptyDescribtion);
		assertEquals(emptyDescribtion, validTrial.getDescription());

		validTrial.setDescription(nullDescribtion);
		assertEquals("", validTrial.getDescription());

		validTrial.setDescription(longDescribtion);
		assertEquals(longDescribtion, validTrial.getDescription());
	}

	@Test
	public void testStartDate() {
		final GregorianCalendar dateOK1 = new GregorianCalendar(1996,
				Calendar.FEBRUARY, 29);
		final GregorianCalendar dateOK2 = new GregorianCalendar(2006,
				Calendar.NOVEMBER, 3);

		validTrial.setStartDate(dateOK1);
		assertEquals(dateOK1, validTrial.getStartDate());

		validTrial.setStartDate(dateOK2);
		assertEquals(dateOK2, validTrial.getStartDate());
	}

	@Test
	public void testEndDate() {
		validTrial.setStartDate(new GregorianCalendar(2000, Calendar.JANUARY,
				2000));

		final GregorianCalendar dateOK1 = new GregorianCalendar(2007,
				Calendar.JANUARY, 1);
		final GregorianCalendar dateOK2 = new GregorianCalendar(2004,
				Calendar.FEBRUARY, 29);
		final GregorianCalendar dateOK3 = new GregorianCalendar(2010,
				Calendar.DECEMBER, 31);

		validTrial.setStartDate(dateOK1);
		assertEquals(dateOK1, validTrial.getStartDate());

		validTrial.setStartDate(dateOK2);
		assertEquals(dateOK2, validTrial.getStartDate());

		validTrial.setStartDate(dateOK3);
		assertEquals(dateOK3, validTrial.getStartDate());
	}

	@Test
	public void testStatus() {
		validTrial.setStatus(Trial.TrialStatus.IN_PREPARATION);
		assertEquals(Trial.TrialStatus.IN_PREPARATION, validTrial.getStatus());

		validTrial.setStatus(Trial.TrialStatus.ACTIVE);
		assertEquals(Trial.TrialStatus.ACTIVE, validTrial.getStatus());

		validTrial.setStatus(Trial.TrialStatus.PAUSED);
		assertEquals(Trial.TrialStatus.PAUSED, validTrial.getStatus());

		validTrial.setStatus(Trial.TrialStatus.FINISHED);
		assertEquals(Trial.TrialStatus.FINISHED, validTrial.getStatus());
	}

	// @Test
	// TODO Implementing Status Date Dependencies in Trial
	public void testStatusDateDependencies() {
		fail("Implementation has not yet been agreed on");
	}

	@Test
	// TODO Implementing Trial Protocol behavior
	public void testProtocol() {

	}

}
