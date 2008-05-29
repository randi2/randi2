package de.randi2.jsf.pages;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

import de.randi2.model.enumerations.Gender;

public class Register {

	private HashMap<String, String> messages;

	private Vector<SelectItem> genderItems;

	private boolean termsPvisible = true;
	
	private boolean regPvisible = false;

	public Register() {
		messages = new HashMap<String, String>();

		messages.put("termsPH", "Terms of use");
		messages
				.put(
						"terms",
						"Disclaimer\n 1. Content The online randomisation system RANDI has been developed with accuracy and caution. DKFZ is not responsible for the topicality, correctness, completeness or quality of the information provided. Liability claims regarding damage caused by the use of any information provided, including any kind of information which is incomplete or incorrect, will therefore be rejected. All offers are not-binding and without obligation. Parts of the pages or the complete publication including all offers and information might be extended, changed or partly or completely deleted by DKFZ without separate announcement.\n2. Referrals and links DKFZ is not responsible for any contents linked or referred to from its pages - unless DKFZ has fully knowledge of illegal contents and would be able to prevent the visitors of his site fromviewing those pages. If any damage occurs by the use of information presented there, only the author of the respective pages might be liable, not the one who has linked to these pages. Furthermore, DKFZ is not liable for any postings or messages published by users of discussion boards, guestbooks or mailinglists provided on his page.\n3. Copyright DKFZ intended not to use any copyrighted material for the publication or, if not possible, to indicatethe copyright of the respective object. The copyright for any material created by the author is reserved. Any duplication or use of objects such as images, diagrams, sounds or texts in other electronic or printed publications is not permitted without the author's agreement.\n4. Privacy policy If the opportunity for the input of personal or business data (email addresses, name, addresses) is given, the input of these data takes place voluntarily. The use and payment of all offered services are permitted - if and so far technically possible and reasonable - without specification of any personal data or under specification of anonymized data or an alias. The use of published postal addresses, telephone or fax numbers and email addresses for marketing purposes is prohibited, offenders sending unwanted spam messages will be punished.\n5. Legal validity of this disclaimer This disclaimer is to be regarded as part of the internet publication which you were referred from. If sections or individual terms of this statement are not legal or correct, the content or validity of the other parts remain uninfluenced by this fact.\n6. CopyrightI nformation included in the Website www.dkfz.de, graphics, fonts and files are subject to national copyright. Any multiplication without written permission of DKFZ is forbidden. \n© Stiftung Deutsches Krebsforschungszentrum (German Cancer Research Center) Heidelberg, Germany. All rights reserved.</html>");

		messages.put("acceptB", "Accept");
		messages.put("declineB", "Decline");
		
		
		messages.put("regPH", "Registration successfull");
		messages.put("regMessage", "A new account was created! An email with a confirmation link will be send shortly.");
		messages.put("okB", "OK");

		messages.put("titel", "RANDI2:Register");
		messages.put("pTitel", "Titel");
		messages.put("firstname", "Fristname");
		messages.put("surname", "Surname");
		messages.put("gender", "Gender");
		messages.put("email", "E-Mail");
		messages.put("phone", "Phone");
		messages.put("mobile", "Mobile");
		messages.put("fax", "Fax");
		messages.put("center", "Center");
		messages.put("assistant", "Assistant");
		messages.put("cPassword", "Center password");

		messages.put("personalH", "Personal Information");
		messages.put("contactH", "Contact Information");
		messages.put("institutionH", "Institution Information");
		messages.put("confirmationH", "Confirmation");

		messages.put("assistantB", "Add");
		messages.put("registerB", "Register");
		messages.put("cancelB", "Cancel");
	}

	public Map<String, String> getMessages() {
		return messages;
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
	
	public void setRegPvisible(boolean value){
		this.regPvisible = value;
	}
	
	public boolean getRegPvisible(){
		return this.regPvisible;
	}

	public void acceptTerms(ActionEvent event) {
		this.termsPvisible = false;
	}

	public void declineTerms(ActionEvent event) {
		this.termsPvisible = true;
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

}
