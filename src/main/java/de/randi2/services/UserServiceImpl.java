package de.randi2.services;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.validator.InvalidStateException;
import org.hibernate.validator.InvalidValue;
import org.springframework.aop.ThrowsAdvice;
import org.springframework.security.annotation.Secured;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.providers.anonymous.AnonymousAuthenticationToken;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import de.randi2.dao.LoginDao;
import de.randi2.dao.PersonDao;
import de.randi2.dao.RoleDao;
import de.randi2.model.Login;
import de.randi2.model.Person;
import de.randi2.model.Role;
import de.randi2.utility.mail.MailServiceInterface;
import de.randi2.utility.mail.exceptions.MailErrorException;

/**
 * 
 * @author dschrimpf
 * 
 */
public class UserServiceImpl implements UserService {

	
	private Logger logger = Logger.getLogger(UserServiceImpl.class);
	
	private LoginDao loginDao;
	private PersonDao personDao;
	
	public UserServiceImpl() {
		super();
	}

	private RoleDao roleDao;
	private MailServiceInterface mailService;

	public UserServiceImpl(LoginDao loginDao, PersonDao personDao,RoleDao roleDao,
			MailServiceInterface mailService) {
		this.loginDao = loginDao;
		this.personDao = personDao;
		this.roleDao = roleDao;
		this.mailService = mailService;
	}

	@Override
	public void addRole(Login login, Role role) {
		// TODO Auto-generated method stub
		throw new RuntimeException("not yet implemented");
	}

	@Override
	public void createRole(Role newRole) {
		roleDao.create(newRole);

	}	
	


	@Override
	public void deleteRole(Role oldRole) {
		// TODO Auto-generated method stub
		throw new RuntimeException("not yet implemented");
	}

	@Override
	public Login prepareInvestigator() {
		Login newUser = new Login();
		newUser.setPerson(new Person());
		newUser.addRole(Role.ROLE_ANONYMOUS);
		AnonymousAuthenticationToken authToken = new AnonymousAuthenticationToken(
				"anonymousUser", newUser, newUser.getAuthorities());
		// Perform authentication
		SecurityContextHolder.getContext().setAuthentication(authToken);
		SecurityContextHolder.getContext().getAuthentication()
				.setAuthenticated(true);
		return newUser;
	}

	@Override
	@Secured({"ROLE_USER","ROLE_ANONYMOUS","ACL_LOGIN_CREATE"})
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public void register(Login newObject) {
		// Investigator Role (self-registration process)
		if (newObject.getRoles().size() == 1
				&& newObject.getRoles().iterator().next().equals(
						Role.ROLE_ANONYMOUS)) {
			newObject.addRole(Role.ROLE_INVESTIGATOR);
		}
		loginDao.create(newObject);
		// send registration Mail
		sendRegistrationMail(newObject);

	}

	@Override
	@Secured({"ROLE_USER","ACL_LOGIN_WRITE"})
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public Login update(Login changedObject) {
		return loginDao.update(changedObject);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public Role updateRole(Role changedRole) {
		return roleDao.update(changedRole);
	}

	@Override
	@Secured({"ROLE_USER", "AFTER_ACL_COLLECTION_READ"})
	@Transactional(propagation=Propagation.SUPPORTS, readOnly=true)
	public List<Login> getAll() {
		return loginDao.getAll();
	}

	@Override
	public void removeRole(Login login, Role role) {
		// TODO Auto-generated method stub
	}

	@Override
	@Secured({"ROLE_USER", "AFTER_ACL_READ"})
	@Transactional(propagation=Propagation.SUPPORTS, readOnly=true)
	public Login getObject(long objectID) {
		return loginDao.get(objectID);
	}

	private void sendRegistrationMail(Login newUser) {
		// Map of variables for the message
		Map<String, Object> newUserMessageFields = new HashMap<String, Object>();
		newUserMessageFields.put("user", newUser);
		newUserMessageFields.put("url", "http://randi2.com/CHANGEME");
		// Map of variables for the subject
		Map<String, Object> newUserSubjectFields = new HashMap<String, Object>();
		newUserSubjectFields.put("firstname", newUser.getPerson()
				.getFirstname());

		Locale language = newUser.getPrefLocale();

		try {
			mailService.sendMail(newUser.getUsername(), "NewUserMail", language,
					newUserMessageFields, newUserSubjectFields);
		} catch (MailErrorException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		if (newUser.getPerson().getTrialSite().getContactPerson() != null) {

			// send e-mail to contact person of current trial-site

			Person contactPerson = newUser.getPerson().getTrialSite()
					.getContactPerson();
			language = contactPerson.getLogin().getPrefLocale();

			// Map of variables for the message
			Map<String, Object> contactPersonMessageFields = new HashMap<String, Object>();
			contactPersonMessageFields.put("newUser", newUser);
			contactPersonMessageFields.put("contactPerson", contactPerson);

			// Map of variables for the subject
			Map<String, Object> contactPersonSubjectFields = new HashMap<String, Object>();
			contactPersonSubjectFields.put("newUserFirstname", newUser
					.getPerson().getFirstname());
			contactPersonSubjectFields.put("newUserLastname", newUser
					.getPerson().getSurname());

			try {
				mailService.sendMail(contactPerson.getEMail(),
						"NewUserNotifyContactPersonMail", language,
						contactPersonMessageFields, contactPersonSubjectFields);
			} catch (MailErrorException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
