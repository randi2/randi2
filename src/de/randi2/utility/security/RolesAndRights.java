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

import java.util.GregorianCalendar;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Restrictions;

import static org.hibernate.criterion.Restrictions.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;

import de.randi2.dao.HibernateAclService;
import de.randi2.model.AbstractDomainObject;
import de.randi2.model.Login;
import de.randi2.model.Person;
import de.randi2.model.Role2;
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
 */
public class RolesAndRights {

	@Autowired private HibernateTemplate template;
	@Autowired private HibernateAclService aclService;

	public void initializeRoles() {
//		if(template.findByExample(Role2.ROLE_ADMIN).isEmpty()) 	template.saveOrUpdate(Role2.ROLE_ADMIN);
//		if(template.findByExample(Role2.ROLE_ANONYMOUS).isEmpty()) 	template.saveOrUpdate(Role2.ROLE_ANONYMOUS);
//		if(template.findByExample(Role2.ROLE_INVESTIGATOR).isEmpty()) 	template.saveOrUpdate(Role2.ROLE_INVESTIGATOR);
//		if(template.findByExample(Role2.ROLE_MONITOR).isEmpty()) 	template.saveOrUpdate(Role2.ROLE_MONITOR);
//		if(template.findByExample(Role2.ROLE_P_INVESTIGATOR).isEmpty()) 	template.saveOrUpdate(Role2.ROLE_P_INVESTIGATOR);
//		if(template.findByExample(Role2.ROLE_STATISTICAN).isEmpty()) 	template.saveOrUpdate(Role2.ROLE_STATISTICAN);
//		if(template.findByExample(Role2.ROLE_USER).isEmpty()) 	template.saveOrUpdate(Role2.ROLE_USER);
		
		template.saveOrUpdate(Role2.ROLE_ADMIN);
		template.saveOrUpdate(Role2.ROLE_ANONYMOUS);
		template.saveOrUpdate(Role2.ROLE_INVESTIGATOR);
		template.saveOrUpdate(Role2.ROLE_MONITOR);
		template.saveOrUpdate(Role2.ROLE_P_INVESTIGATOR);
		template.saveOrUpdate(Role2.ROLE_STATISTICAN);
		template.saveOrUpdate(Role2.ROLE_USER);
		
		
		aclService.createAclwithPermissions(new Login(), Role2.ROLE_ANONYMOUS.getName(),new PermissionHibernate[]{PermissionHibernate.CREATE}, Role2.ROLE_ANONYMOUS.getName());
		aclService.createAclwithPermissions(new Person(), Role2.ROLE_ANONYMOUS.getName(),new PermissionHibernate[]{PermissionHibernate.CREATE}, Role2.ROLE_ANONYMOUS.getName());

	}


	
	public void grantRigths(AbstractDomainObject object, TrialSite scope) {
		// ROLES with TrialSiteScope
		List<Login> logins = template.findByNamedQuery("login.AllLoginsWithRolesAndTrialSiteScope", scope.getId());
		for(Login l: logins){
			for (Role2 r : l.getRoles()) {
				if (r.isScopeTrialSite()) {
					if (object instanceof Login) {
						aclService.createAclwithPermissions(object, l
								.getUsername(), r.getOtherUserPersmissions()
								.toArray(new PermissionHibernate[0]), r
								.getName());
						aclService.createAclwithPermissions(((Login) object)
								.getPerson(), l.getUsername(), r
								.getOtherUserPersmissions().toArray(
										new PermissionHibernate[0]), r
								.getName());
					} else if (object instanceof TrialSubject) {
						aclService.createAclwithPermissions(object, l
								.getUsername(), r.getTrialSubjectPermissions()
								.toArray(new PermissionHibernate[0]), r
								.getName());
					} else if (object instanceof Trial) {
						// TODO Users from other TrialSites
						aclService.createAclwithPermissions(object, l
								.getUsername(), r.getTrialPermissions()
								.toArray(new PermissionHibernate[0]), r
								.getName());
					}
				}
			}
		}
		// ROLES with a other Scope
		logins = template.findByNamedQuery("login.AllLoginsWithRolesAndNotTrialSiteScope");
		for (Login l : logins) {
			for (Role2 r : l.getRoles()) {
				if (!r.equals(Role2.ROLE_USER) && !r.isScopeTrialSite()) {
					if ((object instanceof Login)) {
						aclService.createAclwithPermissions(object, l
								.getUsername(), r.getOtherUserPersmissions()
								.toArray(new PermissionHibernate[0]), r
								.getName());
						aclService.createAclwithPermissions(((Login) object)
								.getPerson(), l.getUsername(), r
								.getOtherUserPersmissions().toArray(
										new PermissionHibernate[0]), r
								.getName());
					}else if (object instanceof TrialSite) {
						aclService.createAclwithPermissions(object, l
								.getUsername(), r.getTrialSitePermissions()
								.toArray(new PermissionHibernate[0]), r
								.getName());
					} else if (object instanceof TrialSubject) {
						aclService.createAclwithPermissions(object, l
								.getUsername(), r.getTrialSubjectPermissions()
								.toArray(new PermissionHibernate[0]), r
								.getName());
					} else if (object instanceof Trial) {
						// TODO Users from other TrialSites
						aclService.createAclwithPermissions(object, l
								.getUsername(), r.getTrialPermissions()
								.toArray(new PermissionHibernate[0]), r
								.getName());
					}
				}
			}
		}
		// Set Right for ROLE_ANONYMOUS
		if (object instanceof TrialSite) {
			aclService.createAclwithPermissions(object, Role2.ROLE_ANONYMOUS
					.getName(), Role2.ROLE_ANONYMOUS.getTrialSitePermissions()
					.toArray(new PermissionHibernate[0]), Role2.ROLE_ANONYMOUS.getName());
		}
	}

	public void registerPersonRole(Login login, Role2 role) {

	}

	public void registerPerson(Login login) {
		for (Role2 role : login.getRoles()) {
			//Grant Create Rigths
			if(role.isCreateUser()){
				aclService.createAclwithPermissions(new Login(), login.getUsername(),
						new PermissionHibernate[]{PermissionHibernate.CREATE}, role.getName());
				aclService.createAclwithPermissions(new Person(), login.getUsername(),
						new PermissionHibernate[]{PermissionHibernate.CREATE}, role.getName());
			}
			if(role.isCreateTrialSite()){
				aclService.createAclwithPermissions(new TrialSite(), login.getUsername(),
						new PermissionHibernate[]{PermissionHibernate.CREATE}, role.getName());
			}
			if(role.isCreateTrialSubject()){
				aclService.createAclwithPermissions(new TrialSubject(), login.getUsername(),
						new PermissionHibernate[]{PermissionHibernate.CREATE}, role.getName());
			}
			if(role.isCreateTrial()){
				aclService.createAclwithPermissions(new Trial(), login.getUsername(),
						new PermissionHibernate[]{PermissionHibernate.CREATE}, role.getName());
			}
			
			if(role.equals(Role2.ROLE_USER)){
				aclService.createAclwithPermissions(login, login.getUsername(),
						role.getOwnUserPermissions().toArray(
								new PermissionHibernate[0]), null);
				aclService.createAclwithPermissions(login.getPerson(), login.getUsername(),
						role.getOwnUserPermissions().toArray(
								new PermissionHibernate[0]), null);
		}else if (role.isScopeTrialSite()) {
			
				//TODO Other User permission
				TrialSite trialSite = login.getPerson().getTrialSite();
				aclService.createAclwithPermissions(trialSite, login
						.getUsername(), role.getTrialSitePermissions().toArray(
						new PermissionHibernate[0]), role.getName());
				for (Trial trial : trialSite.getTrials()) {
					aclService.createAclwithPermissions(trial, login
							.getUsername(), role.getTrialSitePermissions()
							.toArray(new PermissionHibernate[0]), role
							.getName());
					// TODO other TrialSite
					
					//TODO TrialSubjects
				}

			}else{
				if(role.getOtherUserPersmissions().size()>0){
					List<Login> logins = template.find("from Login");
					for(Login o : logins){
						if(!o.equals(login)){
						aclService.createAclwithPermissions(o, login
								.getUsername(), role.getOtherUserPersmissions()
								.toArray(new PermissionHibernate[0]), role
								.getName());
						aclService.createAclwithPermissions(o.getPerson(), login
								.getUsername(), role.getOtherUserPersmissions()
								.toArray(new PermissionHibernate[0]), role
								.getName());
						}
					}		
				}
				if(role.getTrialSitePermissions().size()>0){
					List<TrialSite> trialSites = template.find("from TrialSite");
					for(TrialSite o : trialSites){
						aclService.createAclwithPermissions(o, login
								.getUsername(), role.getTrialSitePermissions()
								.toArray(new PermissionHibernate[0]), role
								.getName());
					}		
				}
				if(role.getTrialPermissions().size()>0){
					List<Trial> trials = template.find("from Trial");
					for(Trial o : trials){
						aclService.createAclwithPermissions(o, login
								.getUsername(), role.getTrialPermissions()
								.toArray(new PermissionHibernate[0]), role
								.getName());
					}		
				}
				if(role.getTrialSubjectPermissions().size()>0){
					List<TrialSubject> trialSubjects = template.find("from TrialSubject");
					for(TrialSubject o : trialSubjects){
						aclService.createAclwithPermissions(o, login
								.getUsername(), role.getTrialSubjectPermissions()
								.toArray(new PermissionHibernate[0]), role
								.getName());
					}		
				}
			}

		}
	}

}
