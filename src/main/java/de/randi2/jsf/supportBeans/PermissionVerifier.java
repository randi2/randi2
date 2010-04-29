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

public class PermissionVerifier {

	@Setter
	private AclService aclService;

	@Setter
	private LoginHandler loginHandler;
	
	@Setter
	private TrialHandler trialHandler;

	public boolean isAllowedCreateUser() {
		return loginHandler.getLoggedInUser().hasPermission(Login.class,
				PermissionHibernate.CREATE)  || loginHandler.getLoggedInUser().hasPermission(Login.class,
						PermissionHibernate.ADMINISTRATION);
	}

	public boolean isAllowedWriteUser() {
		return loginHandler.getLoggedInUser().hasPermission(Login.class,
				PermissionHibernate.WRITE)  || loginHandler.getLoggedInUser().hasPermission(Login.class,
						PermissionHibernate.ADMINISTRATION);
	}
	
	public boolean isAllowedCreateTrial() {
		return loginHandler.getLoggedInUser().hasPermission(Trial.class,
				PermissionHibernate.CREATE) || loginHandler.getLoggedInUser().hasPermission(Trial.class,
						PermissionHibernate.ADMINISTRATION);
	}

	public boolean isAllowedReadTrial() {
		return loginHandler.getLoggedInUser().hasPermission(Trial.class,
				PermissionHibernate.READ)|| loginHandler.getLoggedInUser().hasPermission(Trial.class,
						PermissionHibernate.ADMINISTRATION);
	}

	public boolean isAllowedCreateTrialSite() {
		return loginHandler.getLoggedInUser().hasPermission(TrialSite.class,
				PermissionHibernate.CREATE) || loginHandler.getLoggedInUser().hasPermission(TrialSite.class,
						PermissionHibernate.ADMINISTRATION);
	}

	public boolean isAllowedReadTrialSite() {
		return loginHandler.getLoggedInUser().hasPermission(TrialSite.class,
				PermissionHibernate.READ)|| loginHandler.getLoggedInUser().hasPermission(TrialSite.class,
						PermissionHibernate.ADMINISTRATION);
	}

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
	
	public boolean isAllowedRandomize() {
		return trialHandler.isAddingSubjectsEnabled() && (loginHandler.getLoggedInUser().hasPermission(TrialSubject.class,
				PermissionHibernate.CREATE) || loginHandler.getLoggedInUser().hasPermission(TrialSubject.class,
						PermissionHibernate.ADMINISTRATION));
	}
	
	public boolean isAllowedChangeUserTrialSite(){
		return loginHandler.isEditable() && isAllowedWriteUser();
	}
	
	public boolean isAllowedSeeRandomizationDetails(){
		return loginHandler.getLoggedInUser().hasRole(Role.ROLE_STATISTICAN)||loginHandler.getLoggedInUser().hasRole(Role.ROLE_P_INVESTIGATOR);
	}
}
