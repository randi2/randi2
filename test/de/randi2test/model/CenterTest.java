package de.randi2test.model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import de.randi2.model.AbstractDomainObject;
import de.randi2.model.Center;
import de.randi2test.utility.AbstractDomainTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/META-INF/spring.xml",
		"/META-INF/subconfig/test.xml" })
public class CenterTest extends AbstractDomainTest<Center> {

	private Center validCenter;

	public CenterTest() {
		super(Center.class);
	}

	@Before
	public void setUp() {
		validCenter = factory.getCenter();
	}

	@Test
	public void testConstuctor() {
		Center c = new Center();
		assertEquals("", c.getName());
		assertEquals("", c.getStreet());
		assertEquals("", c.getPostcode());
		assertEquals("", c.getCity());
	}

	@Test
	public void testName() {
		final String nameOK1 = "K";
		final String nameOK2 = stringUtil
				.getWithLength(AbstractDomainObject.MAX_VARCHAR_LENGTH);

		final String nameToLong = stringUtil
				.getWithLength(AbstractDomainObject.MAX_VARCHAR_LENGTH + 1);
		final String nameEmpty = "";
		final String nameNull = null;

		validCenter.setName(nameOK1);
		assertEquals(nameOK1, validCenter.getName());
		assertValid(validCenter);

		validCenter.setName(nameOK2);
		assertEquals(nameOK2, validCenter.getName());
		assertValid(validCenter);

		validCenter.setName(nameToLong);
		assertEquals(nameToLong, validCenter.getName());
		assertInvalid(validCenter, new String[] { "" });

		validCenter.setName(nameEmpty);
		assertEquals(nameEmpty, validCenter.getName());
		assertInvalid(validCenter, new String[] { "" });

		validCenter.setName(nameNull);
		assertEquals("", validCenter.getName());
		assertInvalid(validCenter, new String[] { "" });
	}

	@Test
	public void testStreet() {
		// Street
		validCenter.setStreet(null);
		assertEquals("", validCenter.getStreet());
		assertValid(validCenter);

		validCenter.setStreet("");
		assertEquals("", validCenter.getStreet());
		assertValid(validCenter);

		validCenter.setStreet("Oxford-Street 212");
		assertEquals("Oxford-Street 212", validCenter.getStreet());
		assertValid(validCenter);

		String ok = stringUtil
				.getWithLength(AbstractDomainObject.MAX_VARCHAR_LENGTH);
		validCenter.setStreet(ok);
		assertEquals(ok, validCenter.getStreet());
		assertValid(validCenter);

		String iv = stringUtil
				.getWithLength(AbstractDomainObject.MAX_VARCHAR_LENGTH + 1);
		validCenter.setStreet(iv);
		assertEquals(iv, validCenter.getStreet());
		assertInvalid(validCenter);

	}

	@Test
	public void testPostcode() {
		// Postcode
		validCenter.setPostcode(null);
		assertEquals("", validCenter.getPostcode());
		assertValid(validCenter);

		validCenter.setPostcode("");
		assertEquals("", validCenter.getPostcode());
		assertValid(validCenter);

		validCenter.setPostcode("97321");
		assertEquals("97321", validCenter.getPostcode());
		assertValid(validCenter);

		String ok = stringUtil.getWithLength(Center.MAX_LENGTH_POSTCODE);
		validCenter.setPostcode(ok);
		assertEquals(ok, validCenter.getPostcode());
		assertValid(validCenter);

		String iv = stringUtil.getWithLength(Center.MAX_LENGTH_POSTCODE + 1);
		validCenter.setPostcode(iv);
		assertEquals(iv, validCenter.getPostcode());
		assertInvalid(validCenter);

	}

	@Test
	public void testCity() {
		// City
		validCenter.setCity(null);
		assertEquals("", validCenter.getCity());
		assertValid(validCenter);

		validCenter.setCity("");
		assertEquals("", validCenter.getCity());
		assertValid(validCenter);

		validCenter.setCity("New Hamburger");
		assertEquals("New Hamburger", validCenter.getCity());
		assertValid(validCenter);

		String ok = stringUtil
				.getWithLength(AbstractDomainObject.MAX_VARCHAR_LENGTH);
		validCenter.setCity(ok);
		assertEquals(ok, validCenter.getCity());
		assertValid(validCenter);

		String iv = stringUtil
				.getWithLength(AbstractDomainObject.MAX_VARCHAR_LENGTH + 1);
		validCenter.setCity(iv);
		assertEquals(iv, validCenter.getCity());
		assertInvalid(validCenter);
	}
	
	@Test
	public void testPassword(){
		fail("not yet implemented");
	}

}
