package de.randi2.services;

import java.util.List;

import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.providers.anonymous.AnonymousAuthenticationToken;

import de.randi2.dao.LoginDao;
import de.randi2.dao.PersonDao;
import de.randi2.model.Login;
import de.randi2.model.Person;
import de.randi2.model.Role;
import de.randi2.utility.mail.MailServiceInterface;

/**
 * 
 * @author dschrimpf
 * 
 */
public class UserServiceImpl implements UserService {

	private LoginDao loginDao;
	private PersonDao personDao;
	private MailServiceInterface mailService;

	public UserServiceImpl(LoginDao loginDao, PersonDao personDao,
			MailServiceInterface mailService) {
		this.loginDao = loginDao;
		this.personDao = personDao;
		this.mailService = mailService;
	}

	@Override
	public void addRole(Login login, Role role) {
		// TODO Auto-generated method stub

	}

	@Override
	public void createRole(Role newRole) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteRole(Role oldRole) {
		// TODO Auto-generated method stub

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
	public void register(Login newObject) {
		loginDao.save(newObject);
		// TODO MailSend
		// TODO If the newObject has the Anonymous Role replece it with the
		// Investigator Role (self-registration process)

	}

	@Override
	public void update(Login changedObject) {
		loginDao.save(changedObject);

	}

	@Override
	public void updateRole(Role changedRole) {
		// TODO Auto-generated method stub
	}

	@Override
	public List<Login> getAll() {
		return loginDao.getAll();
	}

	@Override
	public void removeRole(Login login, Role role) {
		// TODO Auto-generated method stub
	}

}
