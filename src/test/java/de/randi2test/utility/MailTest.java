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
		
		String messageTemplate = "NewUserMail";
		Locale language = Locale.GERMANY;
		
		// Map of variables
		Map<String,Object> messageFields = new HashMap<String,Object>();
		messageFields.put("user", factory.getLogin());
		messageFields.put("url", "http://randi2.com");
		// Map of variables
		Map<String,Object> subjectFields = new HashMap<String,Object>();
		subjectFields.put("firstname", factory.getLogin().getPerson().getFirstname());
		 		 
		
		try {
			mailService.sendMail(recipient, messageTemplate, language, messageFields, subjectFields);
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
		
		String messageTemplate = "NewUserMail";
		Locale language = Locale.UK;
		
		// Map of variables
		Map<String,Object> messageFields = new HashMap<String,Object>();
		messageFields.put("user", factory.getLogin());
		messageFields.put("url", "http://randi2.com");
		// Map of variables
		Map<String,Object> subjectFields = new HashMap<String,Object>();
		subjectFields.put("firstname", factory.getLogin().getPerson().getFirstname());
		 		 
		
		try {
			mailService.sendMail(recipient, messageTemplate, language, messageFields, subjectFields);
		} catch (MailErrorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Mail error");
			fail();
		}
		
		
	}		
	
}
