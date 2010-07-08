package de.randi2.utility;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import de.randi2.model.Login;
import de.randi2.test.utility.DomainObjectFactory;
import de.randi2.utility.mail.MailServiceInterface;
import de.randi2.utility.mail.exceptions.MailErrorException;

import static junit.framework.Assert.*;

/**
 * Sends a test email.
 * 
 * 
 * @author Daniel Haehn <dh@randi2.de>
 * 
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/META-INF/spring-test.xml",
		"classpath:/META-INF/subconfig/mail-test.xml" })
public class MailTest {

	@Autowired
	private MailServiceInterface mailService;

	@Autowired
	private DomainObjectFactory factory;

	/**
	 * Recipient for all test e-mails
	 */
	private static String mailAddressForTesting = "dh@randi2.de";

	@Test
	public void sendRegistrationMailGerman() {

		sendRegistrationMail(Locale.GERMANY);

	}

	@Test
	public void sendRegistrationMailEnglish() {

		sendRegistrationMail(Locale.ENGLISH);

	}


	private void sendRegistrationMail(Locale testLocale) {
		Login newUser = factory.getLogin();
		Map<String, Object> newUserMessageFields = new HashMap<String, Object>();
		newUserMessageFields.put("user", newUser);
		newUserMessageFields.put("url", "http://randi2.com/CHANGEME");
		// Map of variables for the subject
		Map<String, Object> newUserSubjectFields = new HashMap<String, Object>();
		newUserSubjectFields.put("firstname", newUser.getPerson()
				.getFirstname());

		mailService.sendMail(newUser.getUsername(), "NewUserMail", testLocale,
					newUserMessageFields, newUserSubjectFields);
		
		
		
		
//		LoginHandler lh = new LoginHandler();
//
//		TrialSite ts = factory.getTrialSite();
//		Person cp = factory.getPerson();
//		cp.setEMail(mailAddressForTesting);
//		ts.setContactPerson(cp);
//
//		Login cpUser = factory.getLogin();
//		cpUser.setUsername(mailAddressForTesting);
//		cpUser.setPrefLocale(testLocale);
//		cp.setLogin(cpUser);
//
//		Login newUser = factory.getLogin();
//		newUser.setUsername(mailAddressForTesting);
//		newUser.getPerson().setTrialSite(ts);
//
//		Locale chosenLocale = testLocale;
//
//		assertEquals(lh.sendRegistrationMails(newUser, chosenLocale, mailService),Randi2.SUCCESS);
//
//		
		
	}
}
