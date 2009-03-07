/* This file is part of RANDI2.
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

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;

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
 * @author Lukasz Plotnicki <lplotni@users.sourceforge.net>
 * @author Daniel Schrimpf <dschrimpf@users.sourceforge.net>
 */
public class RolesAndRights {

	@Autowired
	private HibernateTemplate template;
	@Autowired
	private HibernateAclService aclService;

	public void initializeRoles() {
		// if(template.findByExample(Role2.ROLE_ADMIN).isEmpty())
		// template.saveOrUpdate(Role2.ROLE_ADMIN);
		// if(template.findByExample(Role2.ROLE_ANONYMOUS).isEmpty())
		// template.saveOrUpdate(Role2.ROLE_ANONYMOUS);
		// if(template.findByExample(Role2.ROLE_INVESTIGATOR).isEmpty())
		// template.saveOrUpdate(Role2.ROLE_INVESTIGATOR);
		// if(template.findByExample(Role2.ROLE_MONITOR).isEmpty())
		// template.saveOrUpdate(Role2.ROLE_MONITOR);
		// if(template.findByExample(Role2.ROLE_P_INVESTIGATOR).isEmpty())
		// template.saveOrUpdate(Role2.ROLE_P_INVESTIGATOR);
		// if(template.findByExample(Role2.ROLE_STATISTICAN).isEmpty())
		// template.saveOrUpdate(Role2.ROLE_STATISTICAN);
		// if(template.findByExample(Role2.ROLE_USER).isEmpty())
		// template.saveOrUpdate(Role2.ROLE_USER);

		template.saveOrUpdate(Role.ROLE_ADMIN);
		template.saveOrUpdate(Role.ROLE_ANONYMOUS);
		template.saveOrUpdate(Role.ROLE_INVESTIGATOR);
		template.saveOrUpdate(Role.ROLE_MONITOR);
		template.saveOrUpdate(Role.ROLE_P_INVESTIGATOR);
		template.saveOrUpdate(Role.ROLE_STATISTICAN);
		template.saveOrUpdate(Role.ROLE_USER);

		aclService.createAclwithPermissions(new Login(), Role.ROLE_ANONYMOUS
				.getName(),
				new PermissionHibernate[] { PermissionHibernate.CREATE },
				Role.ROLE_ANONYMOUS.getName());
		aclService.createAclwithPermissions(new Person(), Role.ROLE_ANONYMOUS
				.getName(),
				new PermissionHibernate[] { PermissionHibernate.CREATE },
				Role.ROLE_ANONYMOUS.getName());

	}

	public void grantRigths(AbstractDomainObject object, TrialSite scope) {
		if (object instanceof Login) {
			grantRightsUserObject(object, scope);
		} else if (object instanceof TrialSite) {
			grantRightsTrialSiteObject((TrialSite) object, scope);
		} else if (object instanceof Trial) {
			grantRightsTrialObject((Trial) object, scope);
		} else if (object instanceof TrialSubject) {
			grantRightsTrialSubject((TrialSubject) object, scope);
		}
	}

	
	private void grantRightsUserObject(AbstractDomainObject object,
			TrialSite scope) {
		if (object instanceof Login) {
			// Set Rights for ROLE_ANONYMOUS
			aclService.createAclwithPermissions(object, Role.ROLE_ANONYMOUS
					.getName(),
					new PermissionHibernate[] { PermissionHibernate.READ },
					Role.ROLE_ANONYMOUS.getName());
			aclService.createAclwithPermissions(((Login) object).getPerson(),
					Role.ROLE_ANONYMOUS.getName(),
					new PermissionHibernate[] { PermissionHibernate.READ },
					Role.ROLE_ANONYMOUS.getName());
			// grant rights for other user
			List<Login> logins = template.find("from Login");
			for (Login l : logins) {
				for (Role r : l.getRoles()) {
					if (!r.equals(Role.ROLE_USER)) {
						if (r.isWriteOtherUser()) {
							if (r.isScopeUserWrite()) {
								if (l.getPerson().getTrialSite().getId() == scope
										.getId()) {
									aclService
											.createAclwithPermissions(
													object,
													l.getUsername(),
													new PermissionHibernate[] { PermissionHibernate.WRITE },
													r.getName());
									aclService
											.createAclwithPermissions(
													((Login) object)
															.getPerson(),
													l.getUsername(),
													new PermissionHibernate[] { PermissionHibernate.WRITE },
													r.getName());
								}

							} else {
								aclService
										.createAclwithPermissions(
												object,
												l.getUsername(),
												new PermissionHibernate[] { PermissionHibernate.WRITE },
												r.getName());
								aclService
										.createAclwithPermissions(
												((Login) object).getPerson(),
												l.getUsername(),
												new PermissionHibernate[] { PermissionHibernate.WRITE },
												r.getName());
							}
						}
						if (r.isReadOtherUser()) {
							if (r.isScopeUserRead()) {
								if (l.getPerson().getTrialSite().getId() == scope
										.getId()) {
									aclService
											.createAclwithPermissions(
													object,
													l.getUsername(),
													new PermissionHibernate[] { PermissionHibernate.READ },
													r.getName());
									aclService
											.createAclwithPermissions(
													((Login) object)
															.getPerson(),
													l.getUsername(),
													new PermissionHibernate[] { PermissionHibernate.READ },
													r.getName());
								}
							} else {
								aclService
										.createAclwithPermissions(
												object,
												l.getUsername(),
												new PermissionHibernate[] { PermissionHibernate.READ },
												r.getName());
								aclService
										.createAclwithPermissions(
												((Login) object).getPerson(),
												l.getUsername(),
												new PermissionHibernate[] { PermissionHibernate.READ },
												r.getName());
							}
						}
						if (r.isAdminOtherUser()) {
							aclService
									.createAclwithPermissions(
											object,
											l.getUsername(),
											new PermissionHibernate[] { PermissionHibernate.ADMINISTRATION },
											r.getName());
							aclService
									.createAclwithPermissions(
											((Login) object).getPerson(),
											l.getUsername(),
											new PermissionHibernate[] { PermissionHibernate.ADMINISTRATION },
											r.getName());
						}
					}
				}
			}

		}
	}

	private void grantRightsTrialSiteObject(TrialSite trialSite, TrialSite scope) {
		// Set Right for ROLE_ANONYMOUS
		aclService.createAclwithPermissions(trialSite, Role.ROLE_ANONYMOUS
				.getName(), Role.ROLE_ANONYMOUS.getTrialSitePermissions()
				.toArray(new PermissionHibernate[0]), Role.ROLE_ANONYMOUS
				.getName());

		// Set Rights for other User
		List<Login> logins = template.find("from Login");
		for (Login l : logins) {
			for (Role r : l.getRoles()) {
				if (!r.equals(Role.ROLE_USER)) {
					if (r.isReadTrialSite()) {
						if (r.isScopeTrialSiteView()) {
							if (l.getPerson().getTrialSite().getId() == trialSite
									.getId()) {
								aclService
										.createAclwithPermissions(
												trialSite,
												l.getUsername(),
												new PermissionHibernate[] { PermissionHibernate.READ },
												r.getName());
							}
						} else {
							aclService
									.createAclwithPermissions(
											trialSite,
											l.getUsername(),
											new PermissionHibernate[] { PermissionHibernate.READ },
											r.getName());
						}
					}
					if (r.isWriteTrialSite()) {
						if (r.isScopeTrialSiteWrite()) {
							if (l.getPerson().getTrialSite().getId() == trialSite
									.getId()) {
								aclService
										.createAclwithPermissions(
												trialSite,
												l.getUsername(),
												new PermissionHibernate[] { PermissionHibernate.WRITE },
												r.getName());
							}
						} else {
							aclService
									.createAclwithPermissions(
											trialSite,
											l.getUsername(),
											new PermissionHibernate[] { PermissionHibernate.WRITE },
											r.getName());
						}
					}
					if (r.isAdminTrialSite()) {
						aclService
								.createAclwithPermissions(
										trialSite,
										l.getUsername(),
										new PermissionHibernate[] { PermissionHibernate.ADMINISTRATION },
										r.getName());
					}
				}

			}
		}

	}

	private void grantRightsTrialObject(Trial trial, TrialSite scope) {
		List<Login> logins = template.find("from Login");
		for (Login l : logins) {
			for (Role r : l.getRoles()) {
				if (!r.equals(Role.ROLE_USER)) {
					if (r.isReadTrial()) {
						if (r.isScopeTrialRead()) {
							if (l.getPerson().getTrialSite().getId() == scope
									.getId()) {
								aclService
										.createAclwithPermissions(
												trial,
												l.getUsername(),
												new PermissionHibernate[] { PermissionHibernate.READ },
												r.getName());
							}
						} else {
							aclService
									.createAclwithPermissions(
											trial,
											l.getUsername(),
											new PermissionHibernate[] { PermissionHibernate.READ },
											r.getName());
						}
					}
					if (r.isWriteTrial()) {
						if (r.isScopeTrialWrite()) {
							if (l.getPerson().getTrialSite().getId() == scope
									.getId()) {
								aclService
										.createAclwithPermissions(
												trial,
												l.getUsername(),
												new PermissionHibernate[] { PermissionHibernate.WRITE },
												r.getName());
							}
						} else {
							aclService
									.createAclwithPermissions(
											trial,
											l.getUsername(),
											new PermissionHibernate[] { PermissionHibernate.WRITE },
											r.getName());
						}
					}
					if (r.isAdminTrialSite()) {
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
	}

	private void grantRightsTrialSubject(TrialSubject trialSubject,
			TrialSite scope) {
		// TODO
	}

	public void registerPersonRole(Login login, Role role) {

	}

	public void registerPerson(Login login) {
		for (Role role : login.getRoles()) {
			if (role.equals(Role.ROLE_USER)) {
				aclService.createAclwithPermissions(login, login.getUsername(),
						role.getOwnUserPermissions().toArray(
								new PermissionHibernate[0]), role.getName());
				aclService.createAclwithPermissions(login.getPerson(), login
						.getUsername(), role.getOwnUserPermissions().toArray(
						new PermissionHibernate[0]), role.getName());
			} else {
				// User rights
				if (role.isReadOtherUser()) {
					if (role.isScopeUserRead()) {
						List<Person> list = template.find(
								"from Person p where p.trialSite.id = ?", login
										.getPerson().getTrialSite().getId());
						for (Person p : list) {
							aclService
							.createAclwithPermissions(
									p,
									login.getUsername(),
									new PermissionHibernate[] { PermissionHibernate.READ },
									role.getName());
							if(p.getLogin() == null){
								List<Login> logins =template.find("from Login login where login.person.id = ?", p.getId());
								p.setLogin(logins.get(0));
							}
							aclService
									.createAclwithPermissions(
											p.getLogin(),
											login.getUsername(),
											new PermissionHibernate[] { PermissionHibernate.READ },
											role.getName());
							
						}
					} else {
						List<Person> list = template.find("from Person");
						for (Person p : list) {
							if(p.getLogin() == null){
								List<Login> logins =template.find("from Login login where login.person.id = ?", p.getId());
								p.setLogin(logins.get(0));
							}
							aclService
									.createAclwithPermissions(
											p.getLogin(),
											login.getUsername(),
											new PermissionHibernate[] { PermissionHibernate.READ },
											role.getName());
							aclService
									.createAclwithPermissions(
											p,
											login.getUsername(),
											new PermissionHibernate[] { PermissionHibernate.READ },
											role.getName());
						}
					}
				}
				if (role.isWriteOtherUser()) {
					if (role.isScopeUserWrite()) {
						List<Person> list = template.find(
								"from Person p where p.trialSite.id = ?", login
										.getPerson().getTrialSite().getId());
						for (Person p : list) {
							aclService
									.createAclwithPermissions(
											p.getLogin(),
											login.getUsername(),
											new PermissionHibernate[] { PermissionHibernate.WRITE },
											role.getName());
							aclService
									.createAclwithPermissions(
											p,
											login.getUsername(),
											new PermissionHibernate[] { PermissionHibernate.WRITE },
											role.getName());
						}
					} else {
						List<Person> list = template.find("from Person");
						for (Person p : list) {
							if(p.getLogin() == null){
								List<Login> logins =template.find("from Login login where login.person.id = ?", p.getId());
								p.setLogin(logins.get(0));
							}
							aclService
									.createAclwithPermissions(
											p.getLogin(),
											login.getUsername(),
											new PermissionHibernate[] { PermissionHibernate.WRITE },
											role.getName());
							aclService
									.createAclwithPermissions(
											p,
											login.getUsername(),
											new PermissionHibernate[] { PermissionHibernate.WRITE },
											role.getName());
						}
					}
				}
				if (role.isAdminOtherUser()) {
					List<Person> list = template.find("from Person");
					for (Person p : list) {
						aclService
								.createAclwithPermissions(
										p.getLogin(),
										login.getUsername(),
										new PermissionHibernate[] { PermissionHibernate.ADMINISTRATION },
										role.getName());
						aclService
								.createAclwithPermissions(
										p,
										login.getUsername(),
										new PermissionHibernate[] { PermissionHibernate.ADMINISTRATION },
										role.getName());
					}
				}
				// TrialSite rights
				if (role.isReadTrialSite()) {
					if (role.isScopeTrialSiteView()) {
						aclService
								.createAclwithPermissions(
										login.getPerson().getTrialSite(),
										login.getUsername(),
										new PermissionHibernate[] { PermissionHibernate.READ },
										role.getName());
					} else {
						List<TrialSite> list = template.find("from TrialSite");
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
						aclService
								.createAclwithPermissions(
										login.getPerson().getTrialSite(),
										login.getUsername(),
										new PermissionHibernate[] { PermissionHibernate.WRITE },
										role.getName());
					} else {
						List<TrialSite> list = template.find("from TrialSite");
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
					List<TrialSite> list = template.find("from TrialSite");
					for (TrialSite t : list) {
						aclService
								.createAclwithPermissions(
										t,
										login.getUsername(),
										new PermissionHibernate[] { PermissionHibernate.ADMINISTRATION },
										role.getName());
					}
				}
				// Trial rights
				if (role.isReadTrial()) {
					if (role.isScopeTrialRead()) {
						for (Trial t : login.getPerson().getTrialSite()
								.getTrials()) {
							aclService
									.createAclwithPermissions(
											t,
											login.getUsername(),
											new PermissionHibernate[] { PermissionHibernate.READ },
											role.getName());
						}
					} else {
						List<Trial> list = template.find("from Trial");
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
						for (Trial t : login.getPerson().getTrialSite()
								.getTrials()) {
							aclService
									.createAclwithPermissions(
											t,
											login.getUsername(),
											new PermissionHibernate[] { PermissionHibernate.WRITE },
											role.getName());
						}
					} else {
						List<Trial> list = template.find("from Trial");
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
					List<Trial> list = template.find("from Trial");
					for (Trial t : list) {
						aclService
								.createAclwithPermissions(
										t,
										login.getUsername(),
										new PermissionHibernate[] { PermissionHibernate.ADMINISTRATION },
										role.getName());
					}
				}
				// TrialSubject rights
				// TODO TrialSubject

				// Grant rights create objects
				if (role.isCreateUser()) {
					aclService
							.createAclwithPermissions(
									new Login(),
									login.getUsername(),
									new PermissionHibernate[] { PermissionHibernate.CREATE },
									role.getName());
					aclService
							.createAclwithPermissions(
									new Person(),
									login.getUsername(),
									new PermissionHibernate[] { PermissionHibernate.CREATE },
									role.getName());
				}
				if (role.isCreateTrialSite()) {
					aclService
							.createAclwithPermissions(
									new TrialSite(),
									login.getUsername(),
									new PermissionHibernate[] { PermissionHibernate.CREATE },
									role.getName());
				}
				if (role.isCreateTrial()) {
					aclService
							.createAclwithPermissions(
									new Trial(),
									login.getUsername(),
									new PermissionHibernate[] { PermissionHibernate.CREATE },
									role.getName());
				}
				if (role.isCreateTrialSubject()) {
					aclService
							.createAclwithPermissions(
									new TrialSubject(),
									login.getUsername(),
									new PermissionHibernate[] { PermissionHibernate.CREATE },
									role.getName());
				}
			}
		}

		// for (Role2 role : login.getRoles()) {
		// // Grant Create Rigths
		// if (role.isCreateUser()) {
		// aclService
		// .createAclwithPermissions(
		// new Login(),
		// login.getUsername(),
		// new PermissionHibernate[] { PermissionHibernate.CREATE },
		// role.getName());
		// aclService
		// .createAclwithPermissions(
		// new Person(),
		// login.getUsername(),
		// new PermissionHibernate[] { PermissionHibernate.CREATE },
		// role.getName());
		// }
		// if (role.isCreateTrialSite()) {
		// aclService
		// .createAclwithPermissions(
		// new TrialSite(),
		// login.getUsername(),
		// new PermissionHibernate[] { PermissionHibernate.CREATE },
		// role.getName());
		// }
		// if (role.isCreateTrialSubject()) {
		// aclService
		// .createAclwithPermissions(
		// new TrialSubject(),
		// login.getUsername(),
		// new PermissionHibernate[] { PermissionHibernate.CREATE },
		// role.getName());
		// }
		// if (role.isCreateTrial()) {
		// aclService
		// .createAclwithPermissions(
		// new Trial(),
		// login.getUsername(),
		// new PermissionHibernate[] { PermissionHibernate.CREATE },
		// role.getName());
		// }
		//
		// if (role.equals(Role2.ROLE_USER)) {
		// aclService.createAclwithPermissions(login, login.getUsername(),
		// role.getOwnUserPermissions().toArray(
		// new PermissionHibernate[0]), null);
		// aclService.createAclwithPermissions(login.getPerson(), login
		// .getUsername(), role.getOwnUserPermissions().toArray(
		// new PermissionHibernate[0]), null);
		// } else if (role.isScopeTrialSite()) {
		//
		// // TODO Other User permission
		// TrialSite trialSite = login.getPerson().getTrialSite();
		// aclService.createAclwithPermissions(trialSite, login
		// .getUsername(), role.getTrialSitePermissions().toArray(
		// new PermissionHibernate[0]), role.getName());
		// for (Trial trial : trialSite.getTrials()) {
		// aclService.createAclwithPermissions(trial, login
		// .getUsername(), role.getTrialSitePermissions()
		// .toArray(new PermissionHibernate[0]), role
		// .getName());
		// // TODO other TrialSite
		//
		// // TODO TrialSubjects
		// }
		//
		// } else {
		// if (role.getOtherUserPersmissions().size() > 0) {
		// List<Login> logins = template.find("from Login");
		// for (Login o : logins) {
		// if (!o.equals(login)) {
		// aclService.createAclwithPermissions(o, login
		// .getUsername(), role
		// .getOtherUserPersmissions().toArray(
		// new PermissionHibernate[0]), role
		// .getName());
		// aclService.createAclwithPermissions(o.getPerson(),
		// login.getUsername(),
		// role.getOtherUserPersmissions().toArray(
		// new PermissionHibernate[0]), role
		// .getName());
		// }
		// }
		// }
		// if (role.getTrialSitePermissions().size() > 0) {
		// List<TrialSite> trialSites = template
		// .find("from TrialSite");
		// for (TrialSite o : trialSites) {
		// aclService.createAclwithPermissions(o, login
		// .getUsername(), role.getTrialSitePermissions()
		// .toArray(new PermissionHibernate[0]), role
		// .getName());
		// }
		// }
		// if (role.getTrialPermissions().size() > 0) {
		// List<Trial> trials = template.find("from Trial");
		// for (Trial o : trials) {
		// aclService.createAclwithPermissions(o, login
		// .getUsername(), role.getTrialPermissions()
		// .toArray(new PermissionHibernate[0]), role
		// .getName());
		// }
		// }
		// if (role.getTrialSubjectPermissions().size() > 0) {
		// List<TrialSubject> trialSubjects = template
		// .find("from TrialSubject");
		// for (TrialSubject o : trialSubjects) {
		// aclService.createAclwithPermissions(o, login
		// .getUsername(), role
		// .getTrialSubjectPermissions().toArray(
		// new PermissionHibernate[0]), role
		// .getName());
		// }
		// }
		// }
		//
		// }
	}

}
