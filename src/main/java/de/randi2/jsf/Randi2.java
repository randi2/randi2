package de.randi2.jsf;

import java.io.IOException;
import java.util.Properties;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import com.icesoft.faces.context.effects.Effect;
import com.icesoft.faces.context.effects.EffectBuilder;

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
	
	private String version = null;
	
	private String year = null;
	
	/**
	 * Project official web site.
	 */
	private String website1 = null;
	
	/**
	 * Web site of the main institution.
	 */
	private String website2 = null;
	
	/**
	 * The logo of the main institution.
	 */
	private String logoPath = null;
	
	/**
	 * The contact email of the sysop.
	 */
	private String sysopMail = null;
	
	private final Properties randi2prop = new Properties();
	
	public final static String SUCCESS = "success";
	
	public final static String ERROR = "error";
	
	public Randi2(){
		try {
			randi2prop.load(FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream("/RANDI2.properties"));
		} catch (IOException e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,e.getLocalizedMessage(),e.toString()));
		}
	}
	
	public Effect getAppearEff(){
		return EffectBuilder.build("appear");
	}
	
	public static void showMessage(Exception e){
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,e.getLocalizedMessage(),e.toString()));
	}
	
	public static void showMessage(String message){
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,message,null));
	}
	
	public String getVersion(){
		if(version==null){
//			Properties randi2Prop = new Properties();
//			try {
//				randi2Prop.load(FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream("/RANDI2.properties"));
				version = randi2prop.getProperty("version");
//			} catch (IOException e) {
//				e.printStackTrace();
//				version="ERROR";
//			}
		}
		return version;
	}
	
	public String getWebsite1(){
		if(website1==null){
				website1 = randi2prop.getProperty("website1");
		}
		return website1;
	}
	
	public String getWebsite2(){
		if(website2==null){
				website2 = randi2prop.getProperty("website2");
		}
		return website2;
	}
	
	public String getLogoPath(){
		if(logoPath == null){
				logoPath = randi2prop.getProperty("logoPath");
		}
		return logoPath;
	}
	
	public String getSysopMail(){
		if(sysopMail==null){
				sysopMail = randi2prop.getProperty("sysopMail");
		}
		return sysopMail;
	}
	
	public String getYear(){
		if(year==null){
			year = randi2prop.getProperty("year");
		}
		return year;
	}

}
