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
package de.randi2.jsf.supportBeans;

import static de.randi2.utility.security.ArrayListHelper.permissionsOf;
import static de.randi2.utility.security.ArrayListHelper.sidsOf;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import lombok.Setter;

import org.springframework.security.acls.domain.PrincipalSid;
import org.springframework.security.acls.model.Acl;
import org.springframework.security.acls.model.AclService;
import org.springframework.security.acls.model.NotFoundException;

import de.randi2.jsf.controllerBeans.LoginHandler;
import de.randi2.jsf.controllerBeans.TrialHandler;
import de.randi2.model.Login;
import de.randi2.model.Role;
import de.randi2.model.Trial;
import de.randi2.model.TrialSite;
import de.randi2.model.TrialSubject;
import de.randi2.model.security.ObjectIdentityHibernate;
import de.randi2.model.security.PermissionHibernate;

/**
 * A support bean for the JSF pages or other JSF beans realizing the rights check.
 * 
 * @author L. Plotnicki <l.plotnicki@dkfz.de>
 * 
 */
@ManagedBean(name="permissionVerifier")
@SessionScoped
public class PermissionVerifier {
	@ManagedProperty(value="#{aclService}")
	@Setter
	private AclService aclService;
	@ManagedProperty(value="#{loginHandler}")
	@Setter
	private LoginHandler loginHandler;
	@ManagedProperty(value="#{trialHandler}")
	@Setter
	private TrialHandler trialHandler;

	/**
	 * Checks if the current user has the rights to create an user account 
	 * @return
	 */
	public boolean isAllowedCreateUser() {
		return loginHandler.getLoggedInUser().hasPermission(Login.class,
				PermissionHibernate.CREATE)
				|| loginHandler.getLoggedInUser().hasPermission(Login.class,
						PermissionHibernate.ADMINISTRATION);
	}

	/**
	 *Checks if any user related write operations are allowed for the current user
	 * @return
	 */
	public boolean isAllowedWriteUser() {
		return loginHandler.getLoggedInUser().hasPermission(Login.class,
				PermissionHibernate.WRITE)
				|| loginHandler.getLoggedInUser().hasPermission(Login.class,
						PermissionHibernate.ADMINISTRATION);
	}

	/**
	 * Checks if the current user can create a new trial
	 * @return
	 */
	public boolean isAllowedCreateTrial() {
		return loginHandler.getLoggedInUser().hasPermission(Trial.class,
				PermissionHibernate.CREATE)
				|| loginHandler.getLoggedInUser().hasPermission(Trial.class,
						PermissionHibernate.ADMINISTRATION);
	}

	/**
	 * Checks if the current user can access any trial info
	 * @return
	 */
	public boolean isAllowedReadTrial() {
		return loginHandler.getLoggedInUser().hasPermission(Trial.class,
				PermissionHibernate.READ)
				|| loginHandler.getLoggedInUser().hasPermission(Trial.class,
						PermissionHibernate.ADMINISTRATION);
	}

	/**
	 * Checks if the current user can create a new trial site
	 * @return
	 */
	public boolean isAllowedCreateTrialSite() {
		return loginHandler.getLoggedInUser().hasPermission(TrialSite.class,
				PermissionHibernate.CREATE)
				|| loginHandler.getLoggedInUser().hasPermission(
						TrialSite.class, PermissionHibernate.ADMINISTRATION);
	}

	/**
	 * Checks if the current user can access any trial site objects
	 * @return
	 */
	public boolean isAllowedReadTrialSite() {
		return loginHandler.getLoggedInUser().hasPermission(TrialSite.class,
				PermissionHibernate.READ)
				|| loginHandler.getLoggedInUser().hasPermission(
						TrialSite.class, PermissionHibernate.ADMINISTRATION);
	}

	/**
	 * Checks if the specified trial site can be edited by the current user
	 * @param trialSite - trial site object which should be checked
	 * @return
	 */
	public boolean isAllowedEditTrialSite(TrialSite trialSite) {
		try {
			Acl acl = aclService.readAclById(new ObjectIdentityHibernate(
					TrialSite.class, trialSite.getId()),
					sidsOf(new PrincipalSid(loginHandler.getLoggedInUser()
							.getUsername()) ));
			return acl.isGranted(
					permissionsOf(PermissionHibernate.WRITE, PermissionHibernate.ADMINISTRATION ),
					sidsOf( new PrincipalSid(loginHandler.getLoggedInUser()
							.getUsername()) ), false);
		} catch (NotFoundException e) {
			return false;
		}
	}

	/**
	 * Checks if the specified user account can be edited by the current user
	 * @param user - user object which should be checked
	 * @return
	 */
	public boolean isAllowedEditUser(Login user) {
		try {
			Acl acl = aclService.readAclById(new ObjectIdentityHibernate(
					Login.class, user.getId()),
					sidsOf( new PrincipalSid(loginHandler.getLoggedInUser()
							.getUsername()) ));
			return acl.isGranted(
					permissionsOf(PermissionHibernate.WRITE, PermissionHibernate.ADMINISTRATION ),
					sidsOf( new PrincipalSid(loginHandler.getLoggedInUser()
							.getUsername()) ), false);
		} catch (NotFoundException e) {
			return false;
		}
	}

	/**
	 * Checks if the current user can randomize
	 * @return
	 */
	public boolean isAllowedRandomize() {
		return trialHandler.isAddingSubjectsEnabled()
				&& (loginHandler.getLoggedInUser().hasPermission(
						TrialSubject.class, PermissionHibernate.CREATE) || loginHandler
						.getLoggedInUser().hasPermission(TrialSubject.class,
								PermissionHibernate.ADMINISTRATION));
	}


	/**
	 * Checks if the user can access the randomization data/details
	 * @return
	 */
	public boolean isAllowedSeeRandomizationDetails() {
		Login u = loginHandler.getLoggedInUser();
		return u.hasRole(Role.ROLE_STATISTICAN) || u.hasRole(Role.ROLE_MONITOR)
				|| u.hasRole(Role.ROLE_P_INVESTIGATOR);
	}
}
