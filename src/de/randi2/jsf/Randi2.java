package de.randi2.jsf;

import java.io.IOException;
import java.util.Properties;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 * <p>
 * This class supply other JSF_relevant classes with general methods and fields.
 * </p>
 * 
 * @author Lukasz Plotnicki <lplotni@users.sourceforge.net>
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
public class Randi2 {
	
	public String version = null;
	
	public static String SUCCESS = "success";
	
	public static String ERROR = "error";
	
	public static void showMessage(Exception e){
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,e.getLocalizedMessage(),e.toString()));
	}
	
	public static void showMessage(String message){
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,message,null));
	}
	
	public String getVersion(){
		if(version==null){
			Properties randi2Prop = new Properties();
			try {
				randi2Prop.load(FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream("/RANDI2.properties"));
				version = randi2Prop.getProperty("version");
			} catch (IOException e) {
				e.printStackTrace();
				version="ERROR";
			}
		}
		return version;
	}

}
