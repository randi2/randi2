package de.randi2.jsf.supportBeans;

import java.io.IOException;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.springframework.security.ui.AbstractProcessingFilter;

/**
 * JSF 2 SpringSecurity Bridge.
 * 
 * @author Lukasz Plotnicki <lp@randi2.de>
 * 
 */
public class SecureLogin {

	private String userId;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	private String password;

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public SecureLogin() {
		Exception ex = (Exception) FacesContext
				.getCurrentInstance()
				.getExternalContext()
				.getSessionMap()
				.get(
						AbstractProcessingFilter.SPRING_SECURITY_LAST_EXCEPTION_KEY);

		if (ex != null)
			Randi2.showMessage(ex);

	}

	public void login(ActionEvent evt) throws IOException {
		FacesContext.getCurrentInstance().getExternalContext().redirect(
				FacesContext.getCurrentInstance().getExternalContext()
						.getRequestContextPath()
						+ "/j_spring_security_check?j_username="
						+ userId
						+ "&j_password=" + password);
	}

}
