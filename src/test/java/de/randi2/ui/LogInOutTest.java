package de.randi2.ui;

import static org.junit.Assert.assertEquals;

import org.apache.velocity.anakia.NodeList;
import org.junit.Test;

import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlButton;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlLabel;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlPasswordInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;

public class LogInOutTest {

	private final static String username = "admin@test.de";
	private final static String password = "1$heidelberg";
	private final static String name = "Max Mustermann";
	private final static String loginURL = "http://localhost:8080/RANDI2/login.jspx";
	private final static String goodbyeURL = "http://localhost:8080/RANDI2/goodbye.jspx";

	@Test
	public void loginAndlogoutTest() throws Exception {
		WebClient webClient = new WebClient();
		webClient.setAjaxController(new NicelyResynchronizingAjaxController());
		HtmlPage randi2page;
		// TESTING THE LOGIN PROCESS
		HtmlPage page = webClient.getPage(loginURL);
		HtmlForm form = page.getFormByName("loginForm");
		HtmlButton button = form.getButtonByName("submitButton");
		HtmlTextInput usernameInput = form.getInputByName("j_username");
		HtmlPasswordInput passwordInput = form.getInputByName("j_password");
		usernameInput.setValueAttribute(username);
		passwordInput.setValueAttribute(password);
		randi2page = button.click();
		assertEquals(((HtmlLabel) findElementById(randi2page, "userHeader"))
				.getTextContent(), name);
		// TESTING THE LOGOUT PROCESS
		HtmlPage goodbyePage = findAnchorById(randi2page, "logout").click();
		assertEquals(goodbyePage.getFullyQualifiedUrl("").toString(), goodbyeURL);
	}

	protected HtmlInput findHtmlInputById(HtmlPage page, String id) {
		NodeList elementsByTagName = (NodeList) page
				.getElementsByTagName("input");
		for (int i = 0; i < ((org.w3c.dom.NodeList) elementsByTagName)
				.getLength(); i++) {
			HtmlInput input = (HtmlInput) ((org.w3c.dom.NodeList) elementsByTagName)
					.item(i);
			String currentId = input.getId();

			if (currentId.endsWith(id)) {
				return input;
			}
		}

		return null;
	}

	protected HtmlAnchor findAnchorById(HtmlPage page, String id) {

		for (HtmlAnchor anchor : page.getAnchors()) {
			String currentId = anchor.getId();
			if (currentId.contains(id)) {
				return anchor;
			}
		}

		return null;
	}

	protected HtmlElement findElementById(HtmlPage page, String id) {
		for (HtmlElement e : page.getAllHtmlChildElements()) {
			String currentId = e.getId();
			if (currentId.contains(id)) {
				return e;
			}
		}
		return null;
	}
}
