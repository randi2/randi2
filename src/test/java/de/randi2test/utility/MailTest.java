package de.randi2test.utility;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.junit.Test;
import static junit.framework.Assert.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import de.randi2.utility.mail.MailServiceInterface;
import de.randi2.utility.mail.exceptions.MailErrorException;
/**
 * Sends a test email.
 * 
 * 
 * @author Daniel Haehn <dh@randi2.de>
 *
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/META-INF/spring-test.xml","classpath:/META-INF/subconfig/mail.xml" })
public class MailTest {

	@Autowired
	private MailServiceInterface mailService;
	
	@Autowired
	private DomainObjectFactory factory;
	
	
	@Test
	public void sendmailGerman(){
		
		
		
		String recipient = "dh@randi2.de";
		String subject = "RANDI2 Testbetreff";
		String messageTemplate = "NewUserMail.vm";
		Locale language = Locale.GERMANY;
		
		// Map of variables
		Map<String,Object> templateFields = new HashMap<String,Object>();
		templateFields.put("user", factory.getLogin());
		templateFields.put("url", "http://randi2.com");
		 
		
		try {
			mailService.sendMail(recipient, subject, messageTemplate, language, templateFields);
		} catch (MailErrorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Mail error");
			fail();
		}
		
		
	}	
	

	@Test
	public void sendmailEnglish(){
		
		
		
		String recipient = "dh@randi2.de";
		String subject = "RANDI2 Testsubject";
		String messageTemplate = "NewUserMail.vm";
		Locale language = Locale.UK;
		
		// Map of variables
		Map<String,Object> templateFields = new HashMap<String,Object>();
		templateFields.put("user", factory.getLogin());
		templateFields.put("url", "http://randi2.com");
		 
		
		try {
			mailService.sendMail(recipient, subject, messageTemplate, language, templateFields);
		} catch (MailErrorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Mail error");
			fail();
		}
		
		
	}		
	
}
