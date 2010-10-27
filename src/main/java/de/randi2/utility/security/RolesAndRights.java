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
package de.randi2.utility.security;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import de.randi2.dao.HibernateAclService;
import de.randi2.model.AbstractDomainObject;
import de.randi2.model.Login;
import de.randi2.model.Person;
import de.randi2.model.Role;
import de.randi2.model.Trial;
import de.randi2.model.TrialSite;
import de.randi2.model.TrialSubject;
import de.randi2.model.security.PermissionHibernate;

/**
 * <p>
 * This class realize the mapping between the roles and rights in the RANDI2
 * system.
 * </p>
 * 
 * @author Lukasz Plotnicki <lplotni@users.sourceforge.net>
 * @author Daniel Schrimpf <dschrimpf@users.sourceforge.net>
 */
public class RolesAndRights {

	@Autowired
	private HibernateAclService aclService;

	protected EntityManager entityManager;

	@PersistenceContext
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void grantRights(AbstractDomainObject object, TrialSite scope) {
		// if(scope== null || object == null){
		// throw new RuntimeException();
		// }else
		if (object instanceof Login) {
			grantRightsUserObjectWithScope((Login) object, scope);
			grantRightsUserObjectWithOutScope((Login) object);
		} else if (object instanceof TrialSite) {
			grantRightsTrialSiteObject((TrialSite) object);
		} else if (object instanceof Trial) {
			grantRightsTrialObjectWithScope((Trial) object, scope);
			grantRightsTrialObjectWithOutScope((Trial) object);
		} else if (object instanceof TrialSubject) {
			grantRightsTrialSubject((TrialSubject) object);
		}
	}

	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRED)
	private void grantRightsUserObjectWithScope(Login object, TrialSite scope) {
		// BEGIN grant rights with trial site scope
		// BEGIN Added all acls for logins with a trial site scope
		List<Role> roles = entityManager
				.createQuery(
						"from Role r where (r.writeOtherUser = true and r.scopeUserWrite = true ) or (r.readOtherUser = true and r.scopeUserRead = true)")
				.getResultList();// TODO named query

		for (Role r : roles) {
			List<Login> logins = entityManager
					.createNamedQuery(
							"login.AllLoginsWithSpecificRoleAndTrialSite")
					.setParameter(1, r.getId()).setParameter(2, scope.getId())
					.getResultList();
			for (Login l : logins) {
				if (r.isWriteOtherUser() && r.isScopeUserWrite()) {
					/*
					 * Grant WRITE permission on the new user to the current
					 * user
					 */
					grantRightLogin(object, l.getUsername(), r.getName(),
							PermissionHibernate.WRITE);
				}
				if (r.isReadOtherUser() && r.isScopeUserRead()) {
					/*
					 * IF there is no site-constraint (all users are shown to
					 * the current user)
					 */
					grantRightLogin(object, l.getUsername(), r.getName(),
							PermissionHibernate.READ);
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRED)
	private void grantRightsUserObjectWithOutScope(Login object) {
		// Set Rights for ROLE_ANONYMOUS
		aclService.createAclwithPermissions(object,
				Role.ROLE_ANONYMOUS.getName(),
				new PermissionHibernate[] { PermissionHibernate.READ },
				Role.ROLE_ANONYMOUS.getName());
		aclService.createAclwithPermissions(((Login) object).getPerson(),
				Role.ROLE_ANONYMOUS.getName(),
				new PermissionHibernate[] { PermissionHibernate.READ },
				Role.ROLE_ANONYMOUS.getName());

		List<Role> roles = entityManager
				.createQuery(
						"from Role r where (r.writeOtherUser = true and r.scopeUserWrite = false ) or (r.readOtherUser = true and r.scopeUserRead = false) or r.adminOtherUser = true")
				.getResultList();// TODO named query

		for (Role r : roles) {
			List<Login> logins = entityManager
					.createNamedQuery("login.AllLoginsWithSpecificRole")
					.setParameter(1, r.getId()).getResultList();
			for (Login l : logins) {
				if (r.isWriteOtherUser() && !r.isScopeUserWrite()) {
					/*
					 * IF there is no site-constraint (all users are editable to
					 * the current user)
					 */
					grantRightLogin(object, l.getUsername(), r.getName(),
							PermissionHibernate.WRITE);
				}
				if (r.isReadOtherUser() && !r.isScopeUserRead()) {
					/*
					 * IF there is no site-constraint (all users are shown to
					 * the current user)
					 */
					grantRightLogin(object, l.getUsername(), r.getName(),
							PermissionHibernate.READ);
				}
				if (r.isAdminOtherUser()) {
					/*
					 * If the current user can administrate others
					 */
					grantRightLogin(object, l.getUsername(), r.getName(),
							PermissionHibernate.ADMINISTRATION);
				}
			}
		}
	}

	private void grantRightLogin(Login newLogin, String userName,
			String roleName, PermissionHibernate... permissions) {
		aclService.createAclwithPermissions(newLogin, userName, permissions,
				roleName);
		aclService.createAclwithPermissions(newLogin.getPerson(), userName,
				permissions, roleName);
	}

	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRED)
	private void grantRightsTrialSiteObject(TrialSite trialSite) {
		// Set Right for ROLE_ANONYMOUS
		aclService.createAclwithPermissions(
				trialSite,
				Role.ROLE_ANONYMOUS.getName(),
				Role.ROLE_ANONYMOUS.getTrialSitePermissions().toArray(
						new PermissionHibernate[Role.ROLE_ANONYMOUS
								.getTrialSitePermissions().size()]),
				Role.ROLE_ANONYMOUS.getName());

		// Set Rights for other User
		// find all roles without a trialSite scope and the flags read, write,
		// admin trialSite
		List<Role> roles = entityManager
				.createQuery(
						"from Role r where (r.adminTrialSite=true) or (r.scopeTrialSiteView = false and r.readTrialSite = true) or (r.scopeTrialWrite = false and r.writeTrialSite = true)")
				.getResultList(); // TODO named query

		for (Role r : roles) {
			// find every user with the specific role
			List<Login> logins = entityManager
					.createNamedQuery("login.AllLoginsWithSpecificRole")
					.setParameter(1, r.getId()).getResultList();
			// set acls for the logins
			for (Login l : logins) {
				// grant administration rights
				if (r.isAdminTrialSite()) {
					aclService
							.createAclwithPermissions(
									trialSite,
									l.getUsername(),
									new PermissionHibernate[] { PermissionHibernate.ADMINISTRATION },
									r.getName());
				}
				// grant rights to read the trial site
				if (!r.isScopeTrialSiteView() && r.isReadTrialSite()) {
					aclService
							.createAclwithPermissions(
									trialSite,
									l.getUsername(),
									new PermissionHibernate[] { PermissionHibernate.READ },
									r.getName());
				}
				// grant rights to write/update the trial site
				if (!r.isScopeTrialSiteWrite() && r.isWriteTrialSite()) {
					aclService
							.createAclwithPermissions(
									trialSite,
									l.getUsername(),
									new PermissionHibernate[] { PermissionHibernate.WRITE },
									r.getName());
				}
			}
		}
	}

	/**
	 * Generates all acls for user with trial rights and a trial site scope.
	 * 
	 * @param trial
	 *            The new trial object.
	 * @param scope
	 *            The trial site scope.
	 */
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRED)
	private void grantRightsTrialObjectWithScope(Trial trial, TrialSite scope) {
		if(scope == null) return;
		// BEGIN Added all acls for logins with a trial site scope
		List<Role> roles = entityManager
				.createQuery(
						"from Role r where (r.scopeTrialRead = true and r.readTrial = true ) or (r.scopeTrialWrite = true and r.writeTrial = true)")
				.getResultList();// TODO named query

		for (Role r : roles) {
			List<Login> logins = entityManager
					.createNamedQuery(
							"login.AllLoginsWithSpecificRoleAndTrialSite")
					.setParameter(1, r.getId()).setParameter(2, scope.getId())
					.getResultList();
			for (Login l : logins) {
				// added read permission for this new trial
				if (r.isReadTrial()) {
					aclService
							.createAclwithPermissions(
									trial,
									l.getUsername(),
									new PermissionHibernate[] { PermissionHibernate.READ },
									r.getName());
				}
				// added write permission for this new trial
				if (r.isWriteTrial()) {
					aclService
							.createAclwithPermissions(
									trial,
									l.getUsername(),
									new PermissionHibernate[] { PermissionHibernate.WRITE },
									r.getName());
				}
			}
		}
		// END
	}

	/**
	 * Generates all acls for user with trial rights and without any trial site
	 * scope.
	 * 
	 * @param trial
	 *            The new trial object.
	 * @param scope
	 *            The trial site scope.
	 */
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRED)
	private void grantRightsTrialObjectWithOutScope(Trial trial) {
		// BEGIN Added all acls for logins without a trial site scope
		List<Role> roles = entityManager
				.createQuery(
						"from Role r where (r.scopeTrialRead = false and r.readTrial = true ) or (r.scopeTrialWrite = false and r.writeTrial = true) or r.adminTrial = true")
				.getResultList();// TODO named query
		for (Role r : roles) {
			List<Login> logins = entityManager
					.createNamedQuery("login.AllLoginsWithSpecificRole")
					.setParameter(1, r.getId()).getResultList();
			for (Login l : logins) {
				// added read permission for this new trial
				if (!r.isScopeTrialRead() && r.isReadTrial()) {
					aclService
							.createAclwithPermissions(
									trial,
									l.getUsername(),
									new PermissionHibernate[] { PermissionHibernate.READ },
									r.getName());
				}
				// added write permission for this new trial
				if (!r.isScopeTrialWrite() && r.isWriteTrial()) {
					aclService
							.createAclwithPermissions(
									trial,
									l.getUsername(),
									new PermissionHibernate[] { PermissionHibernate.WRITE },
									r.getName());
				}
				// added admin permission for this new trial
				if (r.isAdminTrial()) {
					aclService
							.createAclwithPermissions(
									trial,
									l.getUsername(),
									new PermissionHibernate[] { PermissionHibernate.ADMINISTRATION },
									r.getName());
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRED)
	private void grantRightsTrialSubject(TrialSubject trialSubject) {
		// all logins which can read the trial from the trialSubject
		List<Login> logins = entityManager
				.createNamedQuery("login.LoginsWithPermission")
				.setParameter(1, Trial.class.getCanonicalName())
				.setParameter(2, trialSubject.getArm().getTrial().getId())
				.setParameter(3, PermissionHibernate.READ.getCode()).getResultList();
		for (Login l : logins) {
			for (Role r : l.getRoles()) {
				if (r.isReadTrialSubject()) {
					aclService
							.createAclwithPermissions(
									trialSubject,
									l.getUsername(),
									new PermissionHibernate[] { PermissionHibernate.READ },
									r.getName());
				}
				if (r.isWriteTrialSubject()) {
					aclService
							.createAclwithPermissions(
									trialSubject,
									l.getUsername(),
									new PermissionHibernate[] { PermissionHibernate.WRITE },
									r.getName());
				}
				if (r.isAdminTrialSubject()) {
					aclService
							.createAclwithPermissions(
									trialSubject,
									l.getUsername(),
									new PermissionHibernate[] { PermissionHibernate.ADMINISTRATION },
									r.getName());
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRED)
	public void registerPersonRole(Login login, Role role) {
		role = entityManager.find(Role.class, role.getId());
		login = entityManager.find(Login.class, login.getId());

		if (role.equals(Role.ROLE_USER)) {
			aclService.createAclwithPermissions(
					login,
					login.getUsername(),
					role.getOwnUserPermissions().toArray(
							new PermissionHibernate[role
									.getOwnUserPermissions().size()]), role
							.getName());
			aclService.createAclwithPermissions(
					login.getPerson(),
					login.getUsername(),
					role.getOwnUserPermissions().toArray(
							new PermissionHibernate[role
									.getOwnUserPermissions().size()]), role
							.getName());
		} else {
			TrialSite site = null;
			try{
				site = (TrialSite) entityManager.createNamedQuery("trialSite.getPersonsTrialSite").setParameter(1, login.getPerson().getId()).getSingleResult();	
			}catch (NoResultException e) {}
			
			newPersonGrantUserRights(login, role, site);
			newPersonGrantUserTrialSite(login, role, site);
			newPersonGrantUserTrial(login, role, site);
			newPersonGrantUserTrialSubject(login, role, site);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	private void newPersonGrantUserRights(Login login, Role role, TrialSite usersSite) {
		//grant create user rights
		if (role.isCreateUser()) {
			aclService.createAclwithPermissions(new Login(),
					login.getUsername(),
					new PermissionHibernate[] { PermissionHibernate.CREATE },
					role.getName());
			aclService.createAclwithPermissions(new Person(),
					login.getUsername(),
					new PermissionHibernate[] { PermissionHibernate.CREATE },
					role.getName());
		}
		if (role.isReadOtherUser()) {
			List<Login> list = new ArrayList<Login>();
			if (role.isScopeUserRead()) {
				if (usersSite != null) {
					list = entityManager
							.createQuery(
									"from Login l where l.person.trialSite.id = ?")
							.setParameter(1,
									usersSite.getId())
							.getResultList();
				}
			} else {
				list = entityManager.createQuery("from Login").getResultList();
			}
			for (Login l : list) {
				if (l != null) {
					grantRightLogin(l, login.getUsername(), role.getName(),
							PermissionHibernate.READ);
				}
			}
		}
		/*
		 * the role can edit other users
		 */
		if (role.isWriteOtherUser()) {
			List<Login> list = new ArrayList<Login>();
			/*
			 * with this role the user can edit other users in the same trial
			 * site, but only if the role contains all roles of the other user
			 */
			if (role.isScopeUserWrite()) {
				if (usersSite != null) {
					List<Login> tempList = entityManager
							.createQuery(
									"from Login l where l.person.trialSite.id = ?")
							.setParameter(1,
									usersSite.getId())
							.getResultList();
					for (Login l : tempList) {
						if (role.getRolesToAssign().containsAll(l.getRoles())) {
							list.add(l);
						}
					}
				}
			}/*
			 * with this role the user can edit all users
			 */
			else {
				list = entityManager.createQuery("from Login").getResultList();
			}
			for (Login l : list) {
				grantRightLogin(l, login.getUsername(), role.getName(),
						PermissionHibernate.WRITE);
			}
		}
		if (role.isAdminOtherUser()) {
			List<Person> list = entityManager.createQuery("from Person")
					.getResultList();
			for (Person p : list) {
				if (p.getLogin() != null) {
					grantRightLogin(p.getLogin(), login.getUsername(),
							role.getName(), PermissionHibernate.ADMINISTRATION);
				}
			}
		}

	}

	@Transactional(propagation = Propagation.REQUIRED)
	private void newPersonGrantUserTrialSite(Login login, Role role, TrialSite usersSite) {
		if (role.isCreateTrialSite()) {
			aclService.createAclwithPermissions(new TrialSite(),
					login.getUsername(),
					new PermissionHibernate[] { PermissionHibernate.CREATE },
					role.getName());
		}
		// TrialSite rights
		if (role.isReadTrialSite()) {
			if (role.isScopeTrialSiteView()) {
				if (usersSite != null) {
					aclService
							.createAclwithPermissions(
									usersSite,
									login.getUsername(),
									new PermissionHibernate[] { PermissionHibernate.READ },
									role.getName());
				}
			} else {
				List<TrialSite> list = entityManager.createQuery(
						"from TrialSite").getResultList();
				for (TrialSite t : list) {
					aclService
							.createAclwithPermissions(
									t,
									login.getUsername(),
									new PermissionHibernate[] { PermissionHibernate.READ },
									role.getName());
				}
			}
		}
		if (role.isWriteTrialSite()) {
			if (role.isScopeTrialSiteWrite()) {
				if (usersSite != null) {
					aclService
							.createAclwithPermissions(
									usersSite,
									login.getUsername(),
									new PermissionHibernate[] { PermissionHibernate.WRITE },
									role.getName());
				}
			} else {
				List<TrialSite> list = entityManager.createQuery(
						"from TrialSite").getResultList();
				for (TrialSite t : list) {
					aclService
							.createAclwithPermissions(
									t,
									login.getUsername(),
									new PermissionHibernate[] { PermissionHibernate.WRITE },
									role.getName());
				}
			}
		}
		if (role.isAdminTrialSite()) {
			List<TrialSite> list = entityManager.createQuery("from TrialSite")
					.getResultList();
			for (TrialSite t : list) {
				aclService
						.createAclwithPermissions(
								t,
								login.getUsername(),
								new PermissionHibernate[] { PermissionHibernate.ADMINISTRATION },
								role.getName());
			}
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	private void newPersonGrantUserTrial(Login login, Role role, TrialSite usersSite) {
		if (role.isCreateTrial()) {
			aclService.createAclwithPermissions(new Trial(),
					login.getUsername(),
					new PermissionHibernate[] { PermissionHibernate.CREATE },
					role.getName());
		}
		// Trial rights
		if (role.isReadTrial()) {
			if (role.isScopeTrialRead()) {
				if (usersSite != null) {
					for (Trial t : usersSite.getTrials()) {
						aclService
								.createAclwithPermissions(
										t,
										login.getUsername(),
										new PermissionHibernate[] { PermissionHibernate.READ },
										role.getName());
					}
					// other Trials
					Query query = entityManager
							.createNamedQuery("trial.AllTrialsWithSpecificParticipatingTrialSite");
					query = query.setParameter(1, usersSite.getId());
					List<Trial> trials = query.getResultList();
					for (Trial t : trials) {
						aclService
								.createAclwithPermissions(
										t,
										login.getUsername(),
										new PermissionHibernate[] { PermissionHibernate.READ },
										role.getName());
					}

				}
			} else {
				List<Trial> list = entityManager.createQuery("from Trial")
						.getResultList();
				for (Trial t : list) {
					aclService
							.createAclwithPermissions(
									t,
									login.getUsername(),
									new PermissionHibernate[] { PermissionHibernate.READ },
									role.getName());
				}
			}
		}
		if (role.isWriteTrial()) {
			if (role.isScopeTrialWrite()) {
				if (usersSite != null) {
					for (Trial t : usersSite.getTrials()) {
						aclService
								.createAclwithPermissions(
										t,
										login.getUsername(),
										new PermissionHibernate[] { PermissionHibernate.WRITE },
										role.getName());
					}
				}
			} else {
				List<Trial> list = entityManager.createQuery("from Trial")
						.getResultList();
				for (Trial t : list) {
					aclService
							.createAclwithPermissions(
									t,
									login.getUsername(),
									new PermissionHibernate[] { PermissionHibernate.WRITE },
									role.getName());
				}
			}
		}
		if (role.isAdminTrial()) {
			List<Trial> list = entityManager.createQuery("from Trial")
					.getResultList();
			for (Trial t : list) {
				aclService
						.createAclwithPermissions(
								t,
								login.getUsername(),
								new PermissionHibernate[] { PermissionHibernate.ADMINISTRATION },
								role.getName());
			}
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	private void newPersonGrantUserTrialSubject(Login login, Role role, TrialSite usersSite) {
		if (role.isCreateTrialSubject()) {
			aclService.createAclwithPermissions(new TrialSubject(),
					login.getUsername(),
					new PermissionHibernate[] { PermissionHibernate.CREATE },
					role.getName());
		}
		// TrialSubject rights
		// TODO TrialSubject

	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void registerPerson(Login login) {
		for (Role role : login.getRoles()) {
			registerPersonRole(login, role);
		}
	}

}
