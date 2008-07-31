package de.randi2.jsf.pages;

import javax.faces.context.*;
import com.icesoft.faces.context.*;

import de.randi2.jsf.Randi2;

import java.util.*;

public class LoginPage{
	
	private String j_username;
	private String j_password;

	public String getJ_password() {
		System.out.println("get pass");
		return j_password;
	}

	public void setJ_password(String j_password) {
		this.j_password = j_password;
	}
	
	public String getJ_username() {
		System.out.println("get username");
		return j_username;
	}


	public void setJ_username(String j_username) {
		this.j_username = j_username;
	}



	public String loginAction() {
		BridgeExternalContext context = (BridgeExternalContext)FacesContext.getCurrentInstance().getExternalContext();
		Iterator iter = context.getRequestParameterNames();
		Map requestMap = context.getRequestParameterMap();
		while( iter.hasNext() ){
			String key = (String)iter.next();
			if( key.endsWith("j_username") ){
				j_username =  (String)requestMap.get(key);	
			}
			else if( key.endsWith("j_password") ){
				j_password =  (String)requestMap.get(key);	
			}
		}
		System.out.println("J_USERNAME:"+j_username+";J_PASSWORD:"+j_password);
		return Randi2.SUCCESS;
	}

}

