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
package de.randi2.jsf.backingBeans;

import java.io.IOException;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import lombok.Getter;
import lombok.Setter;

import com.icesoft.faces.context.effects.Effect;
import com.icesoft.faces.context.effects.Highlight;

import de.randi2.jsf.controllerBeans.LoginHandler;
import de.randi2.model.Person;
import de.randi2.utility.BoxedException;

/**
 * <p>
 * This class contains the logic of the registration.jspx. The page for the
 * registration process.
 * </p>
 * 
 * @author Lukasz Plotnicki <lplotni@users.sourceforge.net>
 */
@ManagedBean(name = "registerPage")
@SessionScoped
public class RegisterPage {
	@ManagedProperty(value = "#{loginHandler}")
	@Setter
	private LoginHandler loginHandler;

	@Getter
	private boolean termsPvisible = true;

	@Getter
	@Setter
	private boolean regPvisible = false;

	public void cancel(ActionEvent event) {
		this.termsPvisible = true;
		loginHandler.cleanUp();
		loginHandler.invalidateSession();
	}

	public void go2Login(ActionEvent event) {
		try {
			FacesContext
					.getCurrentInstance()
					.getExternalContext()
					.redirect(
							FacesContext.getCurrentInstance()
									.getExternalContext()
									.getRequestContextPath()
									+ "/faces/login.xhtml");
		} catch (IOException e) {
			BoxedException.throwBoxed(e);
		}
	}

	public void acceptTerms(ActionEvent event) {
		this.termsPvisible = false;
	}

	public void declineTerms(ActionEvent event) {
		this.termsPvisible = true;
		loginHandler.invalidateSession();
	}

	@Getter
	private boolean createAssistant = false;

	public void setCreateAssistant(boolean createAssistant) {
		if (createAssistant)
			loginHandler.getNewUser().getPerson().setAssistant(new Person());
		else
			loginHandler.getNewUser().getPerson().setAssistant(null);
		this.createAssistant = createAssistant;
	}

	/**
	 * Highlight effect for the confirmation password
	 * 
	 * @return
	 */
	public Effect getEffect() {
		return new Highlight();
	}

}
