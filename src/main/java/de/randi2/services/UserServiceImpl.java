/* 
 * (c) 2008- RANDI2 Core Development Team
 * 
 * This file is part of RANDI2.
 * 
 * RANDI2 is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * RANDI2 is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * RANDI2. If not, see <http://www.gnu.org/licenses/>.
 */
package de.randi2.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.dao.ReflectionSaltSource;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import de.randi2.dao.LoginDao;
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
@Service("userService")
public class UserServiceImpl implements UserService {

	private Logger logger = Logger.getLogger(UserServiceImpl.class);

	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private ReflectionSaltSource saltSourceUser;

	@Autowired
	private LoginDao loginDao;

	@Autowired
	private RoleDao roleDao;
	@Autowired
	private MailServiceInterface mailService;

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void addRole(Login login, Role role) {

		if (login != null && role != null && role.getId() > 0
				&& !login.getRoles().contains(role)) {
			logger.info("user: "
					+ SecurityContextHolder.getContext().getAuthentication()
							.getName() + " add role " + role.getName()
					+ "to user " + login.getUsername());
			login.addRole(role);
			if (login.getId() > 1)
				loginDao.update(login);
		} else
			throw new RuntimeException("");
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void createRole(Role newRole) {
		logger.info("user: "
				+ SecurityContextHolder.getContext().getAuthentication()
						.getName() + " create new role " + newRole.getName());
		roleDao.create(newRole);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
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
				"anonymousUser", newUser, new ArrayList<GrantedAuthority>(
						newUser.getAuthorities()));
		// Perform authentication
		SecurityContextHolder.getContext().setAuthentication(authToken);
		SecurityContextHolder.getContext().getAuthentication()
				.setAuthenticated(true);
		return newUser;
	}

	@Override
	@Secured({ "ROLE_ANONYMOUS", "ACL_LOGIN_CREATE" })
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void register(Login newObject) {
		logger.info("register new user with username/eMail "
				+ newObject.getUsername());
		// Investigator Role (self-registration process)
		if (newObject.getRoles().size() == 1
				&& newObject.getRoles().iterator().next()
						.equals(Role.ROLE_ANONYMOUS)) {
			newObject.addRole(Role.ROLE_INVESTIGATOR);
		}
		newObject.setPassword(passwordEncoder.encodePassword(
				newObject.getPassword(), saltSourceUser.getSalt(newObject)));
		loginDao.create(newObject);
		// send registration Mail
		sendRegistrationMail(newObject);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.randi2.services.UserService#create(de.randi2.model.Login)
	 */
	@Override
	@Secured({ "ACL_LOGIN_CREATE" })
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void create(Login newObject) {
		logger.info("user: "
				+ SecurityContextHolder.getContext().getAuthentication()
						.getName() + " create new user "
				+ newObject.getUsername());
		newObject.setPassword(passwordEncoder.encodePassword(
				newObject.getPassword(), saltSourceUser.getSalt(newObject)));
		loginDao.create(newObject);
		// send registration Mail
		sendRegistrationMail(newObject);
	}

	@Override
	@Secured({ "ACL_LOGIN_WRITE" })
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public Login update(Login changedObject) {
		logger.info("user: "
				+ SecurityContextHolder.getContext().getAuthentication()
						.getName() + " update user "
				+ changedObject.getUsername());
		if (changedObject.getPassword().length() != 64) {
			changedObject.setPassword(passwordEncoder.encodePassword(
					changedObject.getPassword(),
					saltSourceUser.getSalt(changedObject)));
		}
		return loginDao.update(changedObject);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public Role updateRole(Role changedRole) {
		logger.info("user: "
				+ SecurityContextHolder.getContext().getAuthentication()
						.getName() + " update role " + changedRole.getName()
				+ " (id=" + changedRole.getId() + ")");
		return roleDao.update(changedRole);
	}

	@Override
	@Secured({ "ROLE_USER", "AFTER_ACL_COLLECTION_READ" })
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<Login> getAll() {
		logger.info("user: "
				+ SecurityContextHolder.getContext().getAuthentication()
						.getName() + " get all users");
		return loginDao.getAll();
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void removeRole(Login login, Role role) {
		if (login != null && login.getId() > 0 && role != null
				&& role.getId() > 0 && login.getRoles().contains(role)) {
			logger.info("user: "
					+ SecurityContextHolder.getContext().getAuthentication()
							.getName() + " removes role " + role.getName()
					+ " from user " + login.getUsername());
			login.removeRole(role);
			loginDao.update(login);
		} else
			throw new RuntimeException("can't remove role from user");
	}

	@Override
	@Secured({ "ROLE_USER", "AFTER_ACL_READ" })
	@Transactional(propagation = Propagation.SUPPORTS)
	public Login getObject(long objectID) {
		logger.info("user: "
				+ SecurityContextHolder.getContext().getAuthentication()
						.getName() + " get login with id=" + objectID);
		return loginDao.get(objectID);
	}

	private void sendRegistrationMail(Login newUser) {
		// Map of variables for the message
		Map<String, Object> newUserMessageFields = new HashMap<String, Object>();
		newUserMessageFields.put("user", newUser);
		// Map of variables for the subject
		Map<String, Object> newUserSubjectFields = new HashMap<String, Object>();
		newUserSubjectFields.put("firstname", newUser.getPerson()
				.getFirstname());

		Locale language = newUser.getPrefLocale();

		try {
			mailService.sendMail(newUser.getPerson().getEmail(), "NewUserMail",
					language, newUserMessageFields, newUserSubjectFields);
		} catch (MailErrorException e) {
			logger.error("Mail error", e);
		}

		if (newUser.getPerson().getTrialSite() != null
				&& newUser.getPerson().getTrialSite().getContactPerson() != null) {

			// send e-mail to contact person of current trial-site

			Person contactPerson = newUser.getPerson().getTrialSite()
					.getContactPerson();
			language = Locale.getDefault();

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
			contactPersonSubjectFields.put("trialSite", newUser.getPerson().getTrialSite().getName());
			
			try {
				mailService.sendMail(contactPerson.getEmail(),
						"NewUserNotifyContactPersonMail", language,
						contactPersonMessageFields, contactPersonSubjectFields);
			} catch (MailErrorException e) {
				logger.error("Mail error", e);
			}
		}
	}

	@Override
	@Secured({ "ROLE_USER" })
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<Role> getAllRoles() {
		return roleDao.getAll();
	}
}
