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
package de.randi2.jsf.backingBeans;

import java.io.IOException;
import java.util.List;
import java.util.Vector;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

import com.icesoft.faces.context.effects.Effect;
import com.icesoft.faces.context.effects.Highlight;

import de.randi2.jsf.controllerBeans.LoginHandler;
import de.randi2.model.enumerations.Gender;

/**
 * <p>
 * This class contains the logic of the registration.jspx. The page for the
 * registration process.
 * </p>
 * 
 * @author Lukasz Plotnicki <lplotni@users.sourceforge.net>
 */
public class RegisterPage {

	private Vector<SelectItem> genderItems;

	private boolean termsPvisible = true;

	private boolean regPvisible = false;

	public RegisterPage() {
	}

	public List<SelectItem> getGenderItems() {
		if (genderItems == null) {
			genderItems = new Vector<SelectItem>(Gender.values().length);
			for (Gender g : Gender.values()) {
				genderItems.add(new SelectItem(g, g.toString()));
			}
		}
		return genderItems;
	}

	public boolean getTermsPvisible() {
		return this.termsPvisible;
	}

	public void setRegPvisible(boolean value) {
		this.regPvisible = value;
	}

	public boolean getRegPvisible() {
		return this.regPvisible;
	}

	public void cancel(ActionEvent event) {
		this.termsPvisible = true;
		try {
			((LoginHandler) FacesContext.getCurrentInstance().getApplication()
					.getELResolver().getValue(
							FacesContext.getCurrentInstance().getELContext(), null, "loginHandler"))
					.cleanUp();
			((LoginHandler) FacesContext.getCurrentInstance().getApplication()
					.getELResolver().getValue(
							FacesContext.getCurrentInstance().getELContext(), null, "loginHandler"))
					.invalidateSession();
			FacesContext.getCurrentInstance().getExternalContext().redirect(
					FacesContext.getCurrentInstance().getExternalContext()
							.getRequestContextPath()
							+ "/login.jspx");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void go2Login(ActionEvent event){
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect(
					FacesContext.getCurrentInstance().getExternalContext()
							.getRequestContextPath()
							+ "/login.jspx");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void acceptTerms(ActionEvent event) {
		this.termsPvisible = false;
	}

	public void declineTerms(ActionEvent event) {
		this.termsPvisible = true;
		try {
			((LoginHandler) FacesContext.getCurrentInstance().getApplication()
					.getELResolver().getValue(
							FacesContext.getCurrentInstance().getELContext(), null, "loginHandler"))
					.invalidateSession();
			FacesContext.getCurrentInstance().getExternalContext().redirect(
					FacesContext.getCurrentInstance().getExternalContext()
							.getRequestContextPath()
							+ "/login.jspx");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Highlight effect for the confirmation password
	 * @return
	 */
	public Effect getEffect() {
		return new Highlight();
	}

}
