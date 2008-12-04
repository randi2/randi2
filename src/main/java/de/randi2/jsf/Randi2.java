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
package de.randi2.jsf;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
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

	/**
	 * This method returns all found classes in a specified package by going
	 * through the package directory.
	 * 
	 * @param pckgname
	 *            the name of the package (e.g. de.randi2.model.criteria)
	 * @return all found classes
	 * @throws ClassNotFoundException
	 */
	@SuppressWarnings("unchecked")
	public static Class[] getClasses(String pckgname)
			throws ClassNotFoundException {
		ArrayList<Class> classes = new ArrayList<Class>();
		// Get a File object for the package
		File directory = null;
		try {
			ClassLoader cld = Thread.currentThread().getContextClassLoader();
			if (cld == null) {
				throw new ClassNotFoundException("Can't get class loader.");
			}
			String path = pckgname.replace('.', '/');
			URL resource = cld.getResource(path);
			if (resource == null) {
				throw new ClassNotFoundException("No resource for " + path);
			}
			directory = new File(URLDecoder.decode(resource.getPath(), "UTF-8"));
		} catch (NullPointerException x) {
			throw new ClassNotFoundException(pckgname + " (" + directory
					+ ") does not appear to be a valid package");
		} catch (UnsupportedEncodingException x) {
			x.printStackTrace();
		}
		if (directory.exists()) {
			// Get the list of the files contained in the package
			String[] files = directory.list();
			for (int i = 0; i < files.length; i++) {
				// we are only interested in .class files
				if (files[i].endsWith(".class")) {
					// removes the .class extension
					classes.add(Class.forName(pckgname + '.'
							+ files[i].substring(0, files[i].length() - 6)));
				}
			}
		} else {
			throw new ClassNotFoundException(pckgname
					+ " does not appear to be a valid package");
		}
		Class[] classesA = new Class[classes.size()];
		classes.toArray(classesA);
		return classesA;
	}

	public Randi2() {
		try {
			randi2prop.load(FacesContext.getCurrentInstance()
					.getExternalContext().getResourceAsStream(
							"/RANDI2.properties"));
		} catch (IOException e) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, e
							.getLocalizedMessage(), e.toString()));
		}
	}

	public Effect getAppearEff() {
		return EffectBuilder.build("appear");
	}

	public static void showMessage(Exception e) {
		FacesContext.getCurrentInstance().addMessage(
				null,
				new FacesMessage(FacesMessage.SEVERITY_ERROR, e
						.getLocalizedMessage(), e.toString()));
	}

	public static void showMessage(String message) {
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_ERROR, message, null));
	}

	public String getVersion() {
		if (version == null) {
			version = randi2prop.getProperty("version");
		}
		return version;
	}

	public String getWebsite1() {
		if (website1 == null) {
			website1 = randi2prop.getProperty("website1");
		}
		return website1;
	}

	public String getWebsite2() {
		if (website2 == null) {
			website2 = randi2prop.getProperty("website2");
		}
		return website2;
	}

	public String getLogoPath() {
		if (logoPath == null) {
			logoPath = randi2prop.getProperty("logoPath");
		}
		return logoPath;
	}

	public String getSysopMail() {
		if (sysopMail == null) {
			sysopMail = randi2prop.getProperty("sysopMail");
		}
		return sysopMail;
	}

	public String getYear() {
		if (year == null) {
			year = randi2prop.getProperty("year");
		}
		return year;
	}

}
